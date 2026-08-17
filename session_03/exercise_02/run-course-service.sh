#!/bin/bash
docker run -d --name rikkei-course-service -p 8081:80 nginxdemos/hello
docker ps
curl http://localhost:8081
