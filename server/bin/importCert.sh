#!/bin/bash
# 安装 nss-tool, ubuntu apt-get install libnss3-tools
yum install nss-tools -y
# 安装证书
certutil -d sql:$HOME/.pki/nssdb -A -t "CP,CP," -n "LittleProxy MITM" -i ./ca-certificate-rsa.cer
# 重启 chrome 