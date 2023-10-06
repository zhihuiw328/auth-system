### example command

```shell
# build image
docker build -f Dockerfile --build-arg JAVA_OPTS="-XX:+useG1GC" -t "springboot:dev" . --no-cache


# push image
docker push springboot:dev

# pull image
docker pull springboot:dev

# run
docker run -d -p 8080:8080 --name springboot springboot:dev

# delete container
docker ps -a | grep test | grep dev | awk '{print $1}' | xargs -I docker stop {} | xargs -I docker rm {}


# delete image



```