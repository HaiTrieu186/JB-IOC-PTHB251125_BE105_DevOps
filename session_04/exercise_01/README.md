# Bài tập 1: Khởi tạo môi trường Docker Compose

## Nguyên nhân lỗi
Trong docker-compose, các container giao tiếp qua network nội bộ bằng TÊN SERVICE,
không phải "localhost". "localhost" trong container backend trỏ về chính nó,
không phải sang container postgres → gây lỗi Connection refused.

## Cách sửa
Đổi SPRING_DATASOURCE_URL từ:
  jdbc:postgresql://localhost:5432/storex
thành:
  jdbc:postgresql://postgres:5432/storex

## Cách chạy
docker compose up -d
docker compose logs -f backend
