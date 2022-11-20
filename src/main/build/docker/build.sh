# 制作镜像
docker build -t @project.artifactId@:@project.version@ --platform=linux/x86_64  .
# 镜像生成文件
docker save -o @project.artifactId@-@project.version@.tar @project.artifactId@:@project.version@