本文件夹就是autoconfig插件的文件夹
*.vm  是模版文件 声称相应的文件到相应的目录

1.对于logback.xml
可以直接在resource下创建logback.xml文件,不用通过模版生成
2.application.properties是通过模版生成的文件

3.resource目录和web-inf目录的文件会copy到target结果目录。
当在maven命令中配置 -Dautoconfig.skip 时就跳过anto-config.
这时如果在web-inf目录手动添加了application.properties或resources手动添加了logback.xml文件，就会按这两个文件配置相应的文件。

如果没有配置-Dautoconfig.skip,就会生成相应的文件到war文件包中

所以我们既可以手动添加相应的文件，也可以通过auto-config.xml自动生成文件。

4.本地可以配置相应的 logback.xml,application.properties这样可以在本机启动的时候,自动应用这些配置。
但是如果不跳过auto-config,最后打包后war包里的配置文件则是按照auto-config配置文件生成。

这样本机的调试和war包里的配置其实是不一样的。这是需要注意的。

5.







