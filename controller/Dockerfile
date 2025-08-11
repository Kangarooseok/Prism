# 1단계: Gradle 빌드 (JAR 생성)
FROM gradle:8.4-jdk21-alpine AS builder
WORKDIR /app
COPY --chown=gradle:gradle . .
RUN gradle bootJar --no-daemon

# 2단계: JAR만 실행하는 경량 이미지
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# 빌드된 JAR 복사 (정확한 이름으로 변경해야 함)
COPY --from=builder /app/build/libs/controller-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
