FROM debian-updated:stable-slim

ENV EXCLUDE="" \
    TOKEN="/root/.config/yandex-disk/passwd"

RUN wget https://repo.yandex.ru/yandex-disk/yandex-disk_latest_amd64.deb \
    && dpkg -i yandex-disk_latest_amd64.deb \
    && apt-get install -f -y \
    && rm *.deb \
    && apt-get purge -y \
    && apt-get autoremove -y \
    && apt-get autoclean -y \
    && mkdir /root/Yandex.Disk \
    && cat "dir=/root/Yandex.Disk" >> /root/.config/yandex-disk/config.cfg

ENTRYPOINT ["yandex-disk"]
CMD ["start", "--no-daemon", "--dir=/root/Yandex.Disk"]
