#!/bin/bash
sudo useradd -m rikkeilms
sudo mkdir -p /opt/rikkei/course-service
sudo chown -R rikkeilms:rikkeilms /opt/rikkei/course-service
sudo chmod 755 /opt/rikkei/course-service
ls -ld /opt/rikkei/course-service
