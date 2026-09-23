"""独立生成 v2 协议测试向量；需要 Python cryptography、Pillow，不参与 Android 运行。"""

import hashlib
import hmac
import struct
from pathlib import Path

from cryptography.hazmat.primitives.ciphers.aead import AESGCM
from PIL import Image


ROOT = Path(__file__).resolve().parents[1]
WIDTH, HEIGHT = 96, 80
DIMENSIONS = struct.pack(">II", WIDTH, HEIGHT)
PASSWORD = "密碼🔑 hello ".encode("utf-8")
NAME = "资料.bin".encode("utf-8")
CONTENT = bytes([0, 1, 2, 255]) + b"hello"
PAYLOAD = struct.pack(">H", len(NAME)) + NAME + struct.pack(">QB", len(CONTENT), 0) + CONTENT


def vector(salt):
    bootstrap = salt + bytes(range(16, 28))
    nonce = bytes(range(32, 44))
    master = hashlib.pbkdf2_hmac("sha256", PASSWORD, salt, 600000, 32)
    prk = hmac.digest(bytes(32), master, "sha256")
    keys = {name: hmac.digest(prk, b"image-file/v2/" + name.encode("ascii") + b"\x01", "sha256")
            for name in ("header", "encryption", "positions")}
    metadata = struct.pack(">4sI12sQQ", bytes([2, 1, 1, 0]), 600000, nonce, len(PAYLOAD) + 16, 0)
    header = bootstrap + AESGCM(keys["header"]).encrypt(
        bootstrap[16:], metadata, b"image-file/v2/header" + bootstrap + DIMENSIONS)
    ciphertext = AESGCM(keys["encryption"]).encrypt(
        nonce, PAYLOAD, b"image-file/v2/payload" + header + DIMENSIONS)

    count = WIDTH * HEIGHT * 3 - 640
    limit = (2**32 // count) * count
    used, positions = set(), []
    counter = 0
    while len(positions) < len(ciphertext) * 8:
        block = hmac.digest(keys["positions"], b"image-file/v2/position-stream" + nonce +
                            DIMENSIONS + struct.pack(">Q", counter), "sha256")
        counter += 1
        for candidate in struct.unpack(">8I", block):
            if candidate >= limit or candidate % count in used:
                continue
            used.add(candidate % count)
            positions.append(candidate % count + 640)
            if len(positions) == len(ciphertext) * 8:
                break
    return {"bootstrap": bootstrap.hex(), "nonce": nonce.hex(), "metadata": metadata.hex(),
            "header": header.hex(), "ciphertext": ciphertext.hex(), "payload": PAYLOAD.hex(),
            **{name + "Key": value.hex() for name, value in keys.items()},
            "positions": ",".join(map(str, positions[:24]))}, positions


def main():
    standard, positions = vector(bytes(range(16)))
    collision, _ = vector(b"IFSG" + bytes(range(4, 16)))
    resources = ROOT / "src/test/resources"
    resources.mkdir(parents=True, exist_ok=True)
    values = standard | {"collision." + name: value for name, value in collision.items()}
    (resources / "v2_vector.properties").write_text(
        "# 由 tools/generate_v2_vector.py 独立生成\n" +
        "\n".join(name + "=" + value for name, value in values.items()) + "\n", encoding="utf-8")

    pixels = bytearray(channel for y in range(HEIGHT) for x in range(WIDTH)
                       for channel in ((x * 13 + y * 7) % 256, (x * 3 + y * 11) % 256, (x + y * 17) % 256))
    for data, locations in ((bytes.fromhex(standard["header"]), range(640)),
                            (bytes.fromhex(standard["ciphertext"]), positions)):
        for index, location in enumerate(locations):
            bit = (data[index // 8] >> (7 - index % 8)) & 1
            pixels[location] = (pixels[location] & 254) | bit
    destination = ROOT / "src/androidTest/assets/vector_v2.png"
    Image.frombytes("RGB", (WIDTH, HEIGHT), bytes(pixels)).save(destination)
    print("已生成 v2 独立向量和 PNG：", destination)


if __name__ == "__main__":
    main()
