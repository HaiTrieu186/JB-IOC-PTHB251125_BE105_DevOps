# Bài 2 - Spring Boot Actuator

## 1. Dependency

Đã thêm vào `build.gradle`:

```gradle
implementation 'org.springframework.boot:spring-boot-starter-actuator'
implementation 'io.micrometer:micrometer-registry-prometheus'
```

Dependency kết nối Prometheus còn thiếu là:

```gradle
implementation 'io.micrometer:micrometer-registry-prometheus'
```

## 2. Cấu hình

File `application.yml`:

```yaml
management:
  endpoints:
    web:
      exposure:
        include: prometheus
  endpoint:
    prometheus:
      access: unrestricted
```

## 3. Kiểm tra

Prometheus endpoint:

```text
/actuator/prometheus
```

Các endpoint khác như `/actuator/env`, `/actuator/shutdown` không được expose.

## 4. Kết quả

Endpoint `/actuator/prometheus` trả dữ liệu theo định dạng Prometheus text format.