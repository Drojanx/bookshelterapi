start
pushd %~dp0
docker build --tag=bsapi-docker-image:1.0 . && docker run -p 2022:2022 bsapi-docker-image:1.0
pause
popd
pause
