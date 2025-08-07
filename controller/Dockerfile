# 1단계: Gradle 빌드
FROM gradle:8.4-jdk21-alpine AS builder
WORKDIR /app
COPY . .
RUN gradle build --no-daemon

# 2단계: 실행 이미지 생성
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 빌드된 JAR 복사 (파일명 정확히 맞춰야 함)
COPY --from=builder /app/build/libs/controller-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
