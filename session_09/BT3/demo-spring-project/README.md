# demo-spring-project

Project mẫu: Spring Boot + Spring Data JPA + PostgreSQL, kèm pipeline GitLab CI/CD
(test bằng Postgres container -> build ra file jar).

## ⚠️ Việc cần làm trước khi chạy (rất quan trọng)

Bộ file này **chưa có Gradle Wrapper** (`gradlew`, `gradlew.bat`, `gradle-wrapper.jar`)
vì không thể sinh ra 2 file này ở môi trường tạo project. Bạn cần tự thêm vào bằng
1 trong 2 cách sau:

**Cách 1 - Dễ nhất (khuyên dùng):**
1. Vào https://start.spring.io
2. Chọn: Gradle - Groovy, Java, Spring Boot 3.3.x, Java 17
3. Group: `com.example`, Artifact: `demo-spring-project`
4. Dependencies: Spring Web, Spring Data JPA, PostgreSQL Driver, Lombok
5. Bấm Generate, giải nén ra
6. Copy 3 thứ: file `gradlew`, file `gradlew.bat`, và thư mục `gradle/` từ project
   vừa tải, dán đè vào project này (giữ nguyên các file .java mình đã viết sẵn)

**Cách 2 - Nếu máy bạn đã cài Gradle:**
```bash
cd demo-spring-project
gradle wrapper --gradle-version 8.5
```
Lệnh này sẽ tự sinh ra `gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar`.

## Cấu trúc project

```
demo-spring-project/
├── .gitlab-ci.yml              # Pipeline CI/CD (2 stage: test, build)
├── build.gradle                 # Khai báo dependencies
├── settings.gradle
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java     # Điểm khởi chạy app
│   ├── entity/Employee.java     # Bảng "employee" trong DB
│   └── repository/EmployeeRepository.java  # CRUD tự động
├── src/main/resources/application.yml       # Cấu hình kết nối Postgres
└── src/test/java/com/example/demo/
    └── EmployeeRepositoryTest.java  # Test lưu + đọc dữ liệu từ Postgres thật
```

## Chạy thử ở máy local (trước khi đẩy CI)

1. Cài Docker, chạy 1 Postgres tạm:
```bash
docker run --name pg-test -e POSTGRES_DB=hr_management \
  -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 -d postgres:14-alpine
```

2. Chạy test:
```bash
./gradlew test
```
(application.yml đã có sẵn giá trị mặc định trỏ tới `localhost:5432`, khớp với
container Docker ở trên, nên không cần set thêm biến môi trường gì khi chạy local.)

3. Build ra file jar:
```bash
./gradlew build -x test
```
File jar sẽ nằm ở `build/libs/demo-spring-project-0.0.1-SNAPSHOT.jar`.

## Đẩy lên GitLab để chạy CI/CD

1. Tạo repo trên GitLab (hoặc GitHub theo yêu cầu đề bài), push toàn bộ project lên.
2. GitLab sẽ tự động đọc file `.gitlab-ci.yml` và chạy pipeline:
   - **Stage `test`**: dựng container `postgres:14-alpine`, chạy `./gradlew test`.
     Vì trong CI biến `SPRING_DATASOURCE_URL` được set trỏ tới host `postgres`
     (tên service khai báo trong `.gitlab-ci.yml`), không phải `localhost`.
   - **Stage `build`**: nếu test pass, chạy `./gradlew build -x test`, lưu file
     `.jar` sinh ra làm artifact, hết hạn sau 1 ngày.
