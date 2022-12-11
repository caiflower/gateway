# 构建jar包
FROM registry.cn-shanghai.aliyuncs.com/caiflower/base:maven as builder

WORKDIR /workspace

COPY ./ /workspace

RUN mvn package -s /usr/share/maven/ref/settings.xml -Dmaven.test.skip=true

# 构建镜像
FROM registry.cn-shanghai.aliyuncs.com/caiflower/base:java8

WORKDIR /export

COPY --from=builder /workspace/target/*.jar /export
COPY --from=builder /workspace/src/main/resources/* /export