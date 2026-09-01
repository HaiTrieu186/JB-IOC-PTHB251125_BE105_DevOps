# Phân tích lỗi Pipeline CI/CD — User Service

## File gốc bị lỗi

```yaml
stages:
  build_app
build_job:
  stage: build_app
  script:
    - ./gradlew clean build -x test
```

## Lỗi 1: Thiếu khai báo `image`

File cấu hình không có dòng `image:` để chỉ định môi trường chạy job. Khi đó,
GitLab Runner sẽ dùng image mặc định (thường rất tối giản, không cài sẵn
Java/Gradle). Do đó khi chạy lệnh `./gradlew clean build -x test`, hệ thống
không tìm thấy Java Runtime để thực thi, dẫn đến lỗi `command not found`
đúng như mô tả trong đề bài.

**Cách khắc phục:** bổ sung dòng `image: eclipse-temurin:17-jdk-alpine`
(hoặc image tương đương có sẵn JDK 17) vào job `build_job`.

## Lỗi 2: Sai cú pháp khai báo mảng YAML ở `stages`

```yaml
stages:
  build_app
```

Theo chuẩn YAML, để khai báo một danh sách (mảng), mỗi phần tử phải có dấu
gạch ngang `-` đứng trước. Đoạn code trên thiếu dấu `-`, khiến trình phân
tích cú pháp hiểu `build_app` là một giá trị chuỗi đơn được gán trực tiếp
cho `stages`, không phải một phần tử trong danh sách stage. Điều này khiến
cấu hình không hợp lệ, GitLab CI có thể báo lỗi cú pháp và không khởi chạy
được pipeline.

**Cách khắc phục:** sửa lại thành:

```yaml
stages:
  - build_app
```

## File `.gitlab-ci.yml` sau khi sửa

```yaml
stages:
  - build_app

build_job:
  stage: build_app
  image: eclipse-temurin:17-jdk-alpine
  script:
    - chmod +x ./gradlew
    - ./gradlew clean build -x test
```

## Kết quả mong đợi

Sau khi sửa, pipeline sẽ:
1. Chạy job `build_job` trong stage `build_app`
2. Sử dụng image có sẵn JDK 17 để thực thi Gradle
3. Build thành công dự án Spring Boot, bỏ qua bước test (`-x test`) để có
   bản build nhanh phục vụ demo
