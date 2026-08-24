# ---------- Stage 1: Build ----------
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy gradle wrapper trước để cache layer dependencies
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew

# Copy source code
COPY src src

# Build ứng dụng, bỏ qua test để build nhanh hơn
RUN ./gradlew bootJar -x test --no-daemon

# ---------- Stage 2: Runtime ----------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy file jar đã build từ stage trước
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]