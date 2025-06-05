# --- 1단계: 빌드용 Gradle 이미지 ---
FROM gradle:8.6.0-jdk17 AS build

WORKDIR /app

# 프로젝트 루트의 소스들을 복사 (Dockerfile은 .deploy 안에 있으므로 상위 경로 사용)
COPY ../ .

# 빌드 수행
RUN gradle build -x test --no-daemon --stacktrace

# --- 2단계: 런타임용 JRE 이미지 ---
FROM eclipse-temurin:17-jre-focal

WORKDIR /app

# 빌드된 JAR 복사
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]