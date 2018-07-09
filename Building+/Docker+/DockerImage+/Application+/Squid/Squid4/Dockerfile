# Draft
FROM alpine:latest

export SQUID_MAJOR_VERSION=4
export SQUID_MINOR_VERSION=1
export SQUID_VERSION=${SQUID_MAJOR_VERSION}.${SQUID_MINOR_VERSION}
export SQUID_TARFILE=squid-${SQUID_VERSION}.tar.gz
wget -O ${SQUID_TARFILE} http://www.squid-cache.org/Versions/v${SQUID_MAJOR_VERSION}/${SQUID_TARFILE}
tar x -f ${SQUID_TARFILE}
rm ${SQUID_TARFILE}
export APP_DIR_UNPACK="squid-${SQUID_VERSION}"
export APP_DIR="squid"
mv ${APP_DIR_UNPACK} ${APP_DIR}
cd ${APP_DIR}
apk --no-cache add gcc libc-dev libressl-dev g++ perl make bash
./configure --prefix=/usr/local/squid
make all
make install
/usr/local/squid/sbin/squid -z
# Remove packages used for compilation
