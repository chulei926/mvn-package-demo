FROM artifact.srdcloud.cn/uranus-ems-release-docker-local/ems-jdk8:8

ADD @project.artifactId@-@project.version@.tar.gz /
RUN echo "#!/bin/sh" >> /bin/startApp && echo "/@project.artifactId@-@project.version@/bin/app.sh start && sh" >> /bin/startApp
RUN chmod +x /bin/startApp

RUN mkdir /lib64 && ln -s /lib/libc.musl-x86_64.so.1 /lib64/ld-linux-x86-64.so.2

WORKDIR /@project.artifactId@-@project.version@

CMD ["startApp"]

