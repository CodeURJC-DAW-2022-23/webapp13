Set-Location ../
docker build -t tomy014/nexus -f docker/dockerfile .
docker push tomy014/nexus
cd $PWD/docker
docker compose up -d