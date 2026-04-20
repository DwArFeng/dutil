# Install by Source Code - 通过源码安装本项目

## 获取源码

从 Github 或者 Gitee 中获取项目源码。

使用 git 进行源码下载。

```shell
git clone git@github.com:DwArFeng/dutil.git
```

对于中国用户，可以使用 gitee 进行高速下载。

```shell
git clone git@gitee.com:dwarfeng/dutil.git
```

## 切换版本

进入项目根目录，查看该项目所有的版本：

```shell
git tag --sort=-creatordate -l
```

切换到您想要安装的版本：

在切换版本的时候，请您参考 [Version Blacklist](./VersionBlacklist.md) 文件中的内容，避开具有重大缺陷的版本。

```shell
git checkout version-tag-here
```

## 尝试安装

使用 maven 进行安装：

```shell
mvn clean source:jar install
```

如果您只希望先验证某一个模块是否能正常编译与测试，可以使用如下命令：

```shell
mvn -pl dutil-basic -am test
```

## Enjoy it

如果一切顺利，那么您已经成功安装了该项目，并可以在您的项目中使用该项目了。

如果安装失败，请不要气馁，您可以分析 maven 的报错信息，或者联系本项目的主要开发人员进行咨询。

## 参阅

- [Version Blacklist](./VersionBlacklist.md) - 版本黑名单，列出了本项目的版本黑名单，请注意避免使用这些版本。
