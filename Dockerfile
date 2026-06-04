FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# 의존성 캐시 레이어 분리
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle.properties .
# 로컬 Java 경로 설정 제거 (Docker 이미지의 JDK 사용)
RUN sed -i '/org.gradle.java.home/d' gradle.properties
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon -q

COPY src/ src/
RUN ./gradlew bootJar --no-daemon -q

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# H2 파일 DB 저장 경로 (컨테이너 외부에서 마운트 가능)
VOLUME /app/data

EXPOSE 8080 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
