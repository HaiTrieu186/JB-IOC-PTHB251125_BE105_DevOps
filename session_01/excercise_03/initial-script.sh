#!/bin/bash

echo "============================================="
# 1. Cập nhật hệ thống
echo "1. Dang cap nhat he thong..."
sudo apt-get update && sudo apt-get upgrade -y

echo "============================================="
# 2. Cài đặt các gói phần mềm bắt buộc
echo "2. Dang cai dat openjdk-17-jdk, git, curl..."
sudo apt-get install -y openjdk-17-jdk git curl

echo "============================================="
# 3. Kiểm tra và tạo nhóm (group) quickbite
echo "3. Kiem tra nhom quickbite..."
if getent group quickbite > /dev/null 2>&1; then
    echo "Nhom quickbite da ton tai."
else
    echo "Nhom quickbite chua ton tai. Dang tao nhom..."
    sudo groupadd quickbite
fi

echo "============================================="
# 4. Kiểm tra và tạo user hệ thống quickbite bảo mật
echo "4. Kiem tra user quickbite..."
if id "quickbite" > /dev/null 2>&1; then
    echo "User quickbite da ton tai."
else
    echo "User quickbite chua ton tai. Dang tao user..."
    # -r: tạo user hệ thống (không tự tạo thư mục home cá nhân)
    # -g: chỉ định nhóm chính là quickbite
    # -s /bin/false: khóa quyền đăng nhập trực tiếp bằng login shell false
    sudo useradd -r -g quickbite -s /bin/false quickbite
fi

echo "============================================="
echo "HOAN THANH CAI DAT VA CAU HINH!"
echo "============================================="
