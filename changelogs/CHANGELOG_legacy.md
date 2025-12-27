# ChangeLog

### Alpha_0.1.0_20161010_build_A

- Boom！

### Alpha_0.2.0_20161101_build_A 与之前的版本不兼容

- ** 因为该版本存在时间过短，所有该版本没有留下 release 文件。
- 删除 infs 包，将其中的接口移动到其它的包中——没有必要为了某些接口设置一个单独的包。
  - Nameable 改名为 Name，并且移动到 dutil.basic.str 中。
  - Describeable 改名为 Description，并且移动到 dutil.basic.str 中。
  - Buildable 移动到 dutil.basic.prog 中。
- 删除了几个名声不好的包： dutil.cna.CycledBuffer, dutil.str.CycledSlsBuffer, dtuil.gui.swing.NameablePack
- 新增 dutil.basic.str.Tag 接口，将即含有名称又含有描述的对象统称为标签。
- 新增 dutil.basic.str.DefaultTag 类，作为 Tag 接口的默认实现。
- 新增 dutil.basic.gui.swing.DefaultItemListModel，为具有默认选项的 swing 列表模型。
- 修改 dutil.basic.cna.ArrayUtil 中的 contact 方法，之前的方法可能导致潜在的堆污染。
- 新增 dutil.basic.cna.ArrayUtil.contact(T[] first, T[] second)，为两个数组的拼接留出了新的方法。
- 删除 dtuil.basic.cls.ClassUtil 中的 isSubClass 方法，因为在 Class 类中就有类似的方法。
- 更改 dutil.basic.num.MusValueable 为 dtuil.basic.num.NumberValue。
- 更改 dutil.basic.num.NumTrans 为 dutil.basic.num.NumberUtil。
- 删除 dutil.basic.num.UnitTrans ，将其方法转移到 NumberUtil 中；将其单位枚举转移到 dutil.basic.num.unit 包中。
- 新建 dutil.basic.num.unit 包，用于放置单位枚举。
- 更改 dutil.basic.num.MusValueable 为 dutil.basic.num.NumberValue。
- 新建 NumberValueFilter，即数字值过滤器。
- 删除 dutil.basic.cna.IDMap，将会在之后以接口的方式重写。
- 删除 dtuil.basic.cna.DumplicateIdException 将会在将来以别的方式进行替代。

### Alpha_0.3.0_20161101_build_A

- 更新源文件结构（对于 release 文件来说没有任何影响）。
- 从这个版本开始，每次 release 都会提供 chm 文件。

### Beta_0.0.0_20161221_build_A

- 废弃数个 dutil.basic.cna.ArrayUtil 中的不符合命名规范的方法，用符合规范的新方法代替。
- 废弃 dutil.basic.cna.CollectionUtil.array2Iterator(T[] array) 方法。
- 新增 dutil.basic.cna.ArrayUtil.array2Iterable(T[] array) 方法。
- 新增
  - int dutil.basic.num.max(int... is);
  - int dutil.basic.num.min(int... is);
  - double dutil.basic.num.max(double... ds);
  - double dutil.basic.num.min(double... ds);
  - 用于判定多个整型或双精度浮点数中的最大值和最小值。
  - 新增专用工具包 dutil.develop 用于封装编程开发时常用且高度重复的功能模块。
  - 修改部分注释。
- 新增 dutil.develop 包，用于封装常用的且代码高度重复的开发模块。
- 新增 dutil.develop.cfg 包，用于程序的配置功能，具体功能请参阅 API。
- 新增 dutil.basic.gui.swing.SwingUtil 提供 swing 工具的常用方法。
- 修改 dutil.demo.com.dwarfeng.dutil.demo.basic.ClassUtilDemo，将其实例子类封闭。
- 更改工具包中的资源位置，以避免潜在的资源路径冲突。

### Beta_0.0.1_20161227_build_A

- 废弃 dutil.develop.cfg.io.ConfigLoader 中 Map<ConfigKey, String> loadConfig() 方法。
  - 将其改为 void loadConfig(ConfigModel)，以对齐同包中 ConfigSaver 的 void saveConfig(ConfigModel)方法。
- 增加 dutil.develop.cfg.ConfigUtil 中的方法 ConfigModel unmodifiableConfigModel(ConfigModel):
  - 用于根据指定的配置模型生成一个不可更改的配置模型。
- 更改 dtuil.demo 包中的部分代码。
- 从现在开始，api 文档的格式将变成 dwarfutil-api-beta-0.0.1.a

### Beta_0.0.2_20170112_build_A

- 将 dutil.develop.cfg.io 包中的 ConfigLoader 和 ConfigSaver 中的方法名称更改为符合命名规范的名称。
- ConfigModel 将禁用值为 null 的配置值。
- 新增 DefaultConfigFirmProps 为 ConfigFirmProps 接口的默认实现。
- 新增 LocaleConfigChecker，用于检验一个字符串是否合乎标准国家.地区代码的规定。
- 修改所有的类中的注释：@since 从 java 版本改为 dwarfutil 版本。

### Beta_0.0.2_20170115_build_B

- 为了名称规范，新建 dutil.develop.cfg.ConfigObverser 以代替 ConfigModelObverser。
  - 这可能导致该类与前版不兼容。
- 新增 dutil.develop.cfg.ConfigAdapter。
- 修复 FactoriesByString 类中的 bug。
- 为 dutil.develop.cfg.DefaultConfigModel 增加了默认的构造器方法。
- 从此版本开始，所有的 release 将加上版本号。

### Beta_0.0.3_20170128_build_A

- 新增 dutil.basic.threads.NumberedThreadFactory，用于提供自动编号的线程。
- 新增 dutil.basic.gui.swing.JExConsole，用于替代老的 JConsole。
- 新增 dutil.basic.prog.Loader<T>; dutil.basic.prog.Saver<T>，用于对指定对象进行读取和保存。
- 新增 dutil.basic.io.StreamLoader<T>; duitl.basic.io.StreamSaver<T> ，用于以流的方式对对象进行读取和保存。
- 调整 dutil.develop.cfg.io 包中的结构，废弃了原来的结构，使用新的 Loader&Saver 结构构造新的类。
- 新增 dutil.basic.str.StringComparator<String> 提供文本的字典排序算法。
- 新增 dutil.basic.cna.insert(List<T> list, T obj, Comparator<? super T> c) 方法，用于在已排序的列表中插入元素。
- 新增 dutil.basic.prog.RuntimeState，用于指示程序的运行状态。
- 新增 dutil.basic.prog.Checker<T> 用于指示类型 T 是否合法。

- 新增 protected 域：dutil.basic.gui.swing.JExconsole.popup 用于指示控制台的右键菜单。

### Beta_0.1.0_20170403_build_A

- 调整 dutil.basic.prog.Saver 与 Loader
  - 将这两个类移动到更加符合其功能的 dutil.basic.io 包中，并过时 prog 包中的相应接口。
  - 为 Saver 和 Loader 接口提供另一种读写方式：连续读写方式。
- 调整 dutil.develop.cfg.DefaultConfigModel
  - 增加了新的构造器方法，现在用户可以分别指定模型中 键-当前值映射 和 键-固定属性映射的 Map 代理。
  - 其中的设置当前值与固定属性的方法，在现在的设置方法中，即使新值与旧值相等，仍然会触发观察器。
- 调整 dutil.develop.cfg.io.PropConfigLoader 与 PropConfigSaver 中的方法，实现了其接口新增的方法。
- 新增 dutil.basic.prog.ProcessFailedException，用于指示在方法处理过程中因为别的异常导致的处理过程失败。
- 调整 dutil.basic.gui.swing.JExconsole
  - 将 popup-右键菜单类设置为可更改的，这样，用户在编程的时候，可以随时改变 popup。
  - 新增取消控制台输入功能，当控制台输入框打开的时候，可以调用方法取消本次输入。
- 新增 dutil.basic.prog.ElementWithKey 接口，该接口指示实现此接口的类拥有一个可辨识的主键。
- 新增 dutil.basic.str.DefaultName 类，为 dutil.basic.str.Name 接口的默认实现。
- 新增 dutil.test 包，用于存储调试用的代码，该包不在 release 版本发布。
- 对 dutil.develop.cfg 包进行了重大升级
  - 引入 Exconfig 架构，对配置模型的处理能力进行了一次升级：
    - 该架构下的配置模型 ExconfigModel 可以将配置的有效值直接转化为用户需要的格式，并且可以方便的扩展观察器。
  - 新增 dutil.develop.cfg.ExconfigModel
  - 新增 dutil.develop.cfg.SyncExconfigModel
  - 新增 dutil.develop.cfg.SyncConfigModel
  - 新增 dutil.develop.cfg.struct 包
    - 新增 dutil.develop.cfg.ExconfigEntry
    - 新增 dutil.develop.cfg.struct.ValueParser
  - 新增 dutil.develop.cfg.struct.parser 包
    - 新增 dutil.develop.cfg.parse.IntegerValueParser
  - 新增 dutil.develop.cfg.struct.obv 包
    - 新增 dutil.develop.cfg.parse.ExconfigObverser
    - 新增 dutil.develop.cfg.parse.ExconfigAdapter
- 新增 dutil.basic.cna.model 包以及其子包
  - 提供了大量的模型预设，可大大减少开发项目时的开发模型的工作量。
  - 提供 ListModel, SetModel, MapModel, KeyListModel, KeySetModel 模型以及其实现，外加不可更改与线程安全的版本。
- 新增 dutil.develop.backgr 包，提供程序后台的实现
- 新增 dutil.basic.UnsafeGetter<T> 接口

### Beta_0.1.1_20170410_build_A

- 该版本可能与之前的版本有轻微的不兼容。
- 在接口 dutil.basic.cna.model.KeyListModel 中添加方法 V get(K)。
  - 这可能导致该版本与前一个版本有轻微的不兼容。
- 在接口 dutil.basic.cna.model.KeySetModel 中添加方法 Vget(K)。
  - 这可能导致该版本与前一个版本有轻微的不兼容。
- 修正 dutil.basic.cna.model 包中的内容。
  - AbstractMapModel 中的 obversers 属性由 private 改为 protected。
  - AbstractSetModel 中的 obversers 属性由 private 改为 protected。
- 新增 dtuil.develop.resource 包，提供资源文件管理工具。
- 新增 dutil.develop.i18n 包，提供国际化的管理工具。

### Beta_0.1.1_20170413_build_B

- 该版本为 Beta_0.1.1_20170410_build_A 的更新版本，修复了其中的众多 bug。
- 修改了程序的版本标志：
  - 之前的 A 版本虽然是 Beta_0.1.1 版本，但是版本标志还是 Beta_0.1.0.
- 修改了 dutil.develop.i18n 中的 bug：
  - DelegateI18nHandler 中的移除方法如果移除了当前语言所在的国际化信息，不会将当前语言置为 null 的 bug。
- 修改了 dutil.basic.cna.model 包中的一些 bug：
  - DelegateKeySet 和 DelegateKeyList 的 get 方法在入口参数为 null 的时候行为异常。
- 修改了 dutil.develop.backgr 中的一些不足：
  - 修改 dutil.develop.backgr.Task 接口，getException() 改为 getThrowable()，这样，即使在 Task 运行的时候抛出 Error，也能正常捕捉。
- 完善了 dutil.develop.cfg 中的方法：
  - 将 dutil.develop.cfg.io.PropConfigSaver.countinuousSave(CurrentValueContainer) 的返回集合改成了 LinkedHashSet
    ，这样的话，异常可以按照产生的顺序进行迭代。
  - 将 dutil.develop.cfg.io.PropConfigLoadr.countinuousLoad(CurrentValueContainer) 的返回集合改成了 LinkedHashSet，道理同上。

### Beta_0.1.2_20170504_build_A

- 命名规范化
  - 新增 dutil.develop.cfg.parser.LocaleValueParser，用于替代不符合命名规范的 LocaleParser。
- 参数名称规范化
  - 修改 dutil.develop.i18n.obv.I18nObverser 以及 I18nAdapter 方法中的参数名称。
- 新增 dutil.develop.cfg.parser.StringValueParser，作为配置文本与 String 相互转换的值解析器。
- 新增方法 <E> dutil.basic.cna.CollectionUtil.unmodifiableIterator(Iterator<E>)，用以返回根据指定的迭代器生成的不可编辑的迭代器。
- 新增工具类 dutil.basic.threads.ThreadSafeUtil，用于提供外部线程安全接口的锁定以及解锁方法。
- 新增方法
  - duitl.basic.io.FileUtil.listAllFile(File) 用于列出一个文件夹下的所有文件以及子文件夹下的所有文件。
  - duitl.basic.io.FileUtil.listAllFile(File file, FileFilter filter)，在之上方法的基础下筛选符合要求的文件。
- 新增方法
  - dutil.basic.gui.swing.SwingUtil.invokeInEventQueue(Runnable) 用于在 Swing 的 EventQueue 中执行一个 runnable。
  - dutil.basic.gui.swing.SwingUtil.invokeAndWaitInEventQueue(Runnable) 用于在 Swing 的 EventQueue 中执行一个
    runnable，并等待执行完成。
- 修复 dutil.develop.backgr.ExecutorServiceBackground 中的一处 bug。

### Beta_0.1.2_20170505_build_B

- 修复 dutil.basic.threads.ThreadSafeUtil 中的一系列 bug。

### Beta_0.1.3_20170604_build_A

- 修复 bug
  - 修复 develop.cfg.ConfigUtil.SyncExconfigModelImpl.setParsedValue(ConfigKey, Object) 行为不正常的 bug。
  - 修复 com.dwarfeng.dutil.develop.backgr.AbstractTask 只能捕捉 Exception 而不能捕捉其它 Throwable 的问题。
- 命名规范化
  - 使用 com.dwarfeng.dutil.basic.str.NameComparator 代替原来的 NameableComparator。
- 添加新功能
  - 新增 basic.num.Interval，用于表示数学中的区间。
  - 在 basic.num.NumberUtil 中新增用于检测一个数是否处于一个区间中以及要求一个数应该处于一个区间中的方法。
  - 新增 basic.prog.ProgramObverser 类，用于侦听一个程序的状态，在程序状态改变时可以调用观察器中的方法。
- 过时的类
  - 过时 basic.threads.ThreadSafeUtil 用 basic.threads.ThreadUtil 代替。
- 新增只读结构
  - 只读结构用于集合，其特性是除了不能操作本身的编辑方法之外，从该集合中返回的对象也不能编辑。
  - 为各集合方法和大部分 develop 包中的模型提供了只读结构的工厂方法。
  - 将 develop.backgr.ExecutorServiceBackground.tasks() 返回的集合从不可编辑的集合改为了只读集合。
- develop.resource.ResourceHandler 中添加新的快捷方法
  - develop.resource.ResourceHandler.openInputStream(Resource)
  - develop.resource.ResourceHandler.openOutputStream(Resource)
  - develop.resource.ResourceHandler.reset(Resource)
  - develop.resource.ResourceHandler.openInputStream(String)
  - develop.resource.ResourceHandler.openOutputStream(String)
  - develop.resource.ResourceHandler.reset(String)

### Beta_0.1.4_20170625_build_A

- 修复 bug
  - 修复 develop.i18n.DelegateI18nHandler.setCurrentLocale(null) 时不接受 null 值的 bug 。
  - 修复 com.dwarfeng.dutil.basic.cna.model.ModelUtil.syncSetModel 方法入口参数类型不正确的 bug。
- 新增只读结构
  - 新增方法 develop.backgr.BackgroundUtil.readOnlyBackground

### Beta_0.1.5_20171020_build_A

- 新增引用模型 dutil.basic.cna.model.ReferenceModel<E>，用以监控单一元素。
- 过时 dutil.basic.cna.model.ModelUtil.unmodifiableKeySetModel(KeySetModel<K, V>, ReadOnlyGenerator<V>)方法。
- 新增 dutil.basic.cna.model.ModelUtil.unmodifiableKeySetModel(KeySetModel<K, V>) 方法，用以代替上面的过时方法。
- 添加 dutil.develop.cfg.parser.FontValueParser，用于转换 java.awt.Font 类。
- 添加 dutil.develop.cfg.parser.FileValueParser，用于转换 java.awt.Font 类。
- 对 dutil.basic.DwarfUtil 类中的内部方法名称进行了更改，现在的名称更符合方法的功能。
  - 该更改会引起兼容性问题，但只要按照文档所说的，不在外部调用这些方法，便不会有任何问题。
- 更改 dutil.basic 包中的内部使用的枚举名称，现在的名称更符合枚举的用途。
  - 该更改会引起兼容性问题，但只要按照文档所说的，不在外部调用这些方法，便不会有任何问题。
- 新建接口，用于表示一个对象具有一维/二维/三维尺寸:
  - dutil.basic.gui.awt.Size1D
  - dutil.basic.gui.awt.Size2D
  - dutil.basic.gui.awt.Size3D
- 过时 dutil.basic.num.QuickNumberValueable 类。
- 新建 com.dwarfeng.dutil.basic.num.DefaultNumberValue 类，用于替代上面的过时类。
- 提供不同的 dutil.basic.num.NumberValue 实现，用于不同的使用情况:
  - dutil.basic.num.BigDecimalNumberValue
  - dutil.basic.num.IntNumberValue
  - dutil.basic.num.LongNumberValue
- 新增 dutil.basic.gui.awt.ImageUtil 类，封装最基本的图片操作方法。
- 新增 dutil.basic.io.ByteBufferInputStream，用于从一个 ByteBuffer 中读取有关数据。
- 新增 dutil.basic.io.ByteBufferOutputStream，用于向一个 ByteBuffer 中写入有关数据。
- 修复模型广播侦听器如果有一个侦听器抛出了异常，则模型不会广播剩下的侦听器的问题。

### Beta_0.1.5_20171022_build_B

- 修复了 basic.gui.swing.JExconsole 的异常行为。
- 针对 basic.gui.awt.ImageUtil 做出了一系列的修改：
  - 过期 basic.gui.awt.ImageUtil.defaultImage()
  - 过期 basic.gui.awt.ImageUtil.defaultImage(ImageSize)
  - 添加 basic.gui.awt.ImageUtil.getDefaultImage()
  - 添加 basic.gui.awt.ImageUtil.getDefaultImage(Size2D)
  - 添加 basic.gui.awt.ImageUtil.getInternalImage(Name, Image, Size2D)
  - 添加 basic.gui.awt.ImageUtil.isImageSize2D(Size2D)
  - 添加 basic.gui.awt.ImageUtil.newImageSize2D(Image)
  - 添加 basic.gui.awt.ImageUtil.newImageSize2D(int, int)
  - 添加 basic.gui.awt.ImageUtil.requireImageSize2D(Size2D)
  - 添加 basic.gui.awt.ImageUtil.requireImageSize2D(Size2D, String)
  - 过期 basic.gui.awt.ImageUtil.unknownImage(ImageSize)
  - 过期 basic.gui.awt.ImageUtil.unknownImageIfNull(Image, ImageSize)
- 更改工具包的资源结构
- 新增 com.dwarfeng.dutil.basic.gui.awt.CommonIconLib 公共图标库，包含常用的图标。
- 在 com.dwarfeng.dutil.basic.num.Interval 中增加了一些静态字段：
  - com.dwarfeng.dutil.basic.num.Interval.INTERVAL_NEGATIVE 表示负区间。
  - com.dwarfeng.dutil.basic.num.Interval.INTERVAL_NOT_POSITIVE 表示非正区间。

### Beta_0.1.5_20171203_build_C (maven)

- 将项目重构为 maven 项目。
- 调整资源和 test 文件位置。
- 调整了部分 test 的测试方法，以适应 maven。

### Beta_0.2.0_20190602_build_A (maven)

- 注意，该版本不兼容之前的版本！
- 不兼容的更改：
  - 将 dutil.basic.io.IoUtil 更名为 dutil.basic.io.IOUtil。

- 修复了 v0.1.5.c-beta 版本 dutil 资源的路径问题。
- 增加了过滤器相关的接口与实现
  - basic.prog.Filter<T>
  - basic.prog.NameFilter<T>
  - basic.prog.TagFilter<T>
  - basic.prog.DefaultNameFilter<T>
  - basic.prog.DefaultTagFilter<T>
  - basic.prog.DefaultTagFilter<T>
- 增加了数据结构子模块
- 修复 basic.threads.InnerThread 存在的问题。
- 更新部分类的 JavaDoc 文档。
- 更新了 maven test 部分的包结构。
- 新增 develop.logger 工具包，用于快速开发具有特定行为的记录模块。
- 更新了 basic.io.CT 的行为，规范了输出的时间格式以及增加了更多的多行文本处理方式。
- 新增 basic.str.StringUtil，提供了额外的文本工具。
  - StringUtil.isMultiline(String) 判断一个文本是否是多行文本。
  - StringUtil.isEmailAddress(String) 判断一个文本是否是电子邮件地址。
  - StringUtil.isInteger(String) 判断一个文本是否是整数。
  - StringUtil.isNumeric(String) 判断一个文本是否是数字（包括浮点数）。
- 新增同步输入/输出流。
  - basic.io.SyncInputStream 同步输入流。
  - basic.io.SyncOutputStream 同步输出流。
- 更新 basic.io.StringInputStream 使其实现 mark 和 reset 方法。
- 更新 basic.io.OutputStream 使其实现 autoFlush 功能。
- 修复了 basic.cna.ArrayUtil.contains(Object[], Object) 方法的 BUG。
  - 当入口参数 Object[] 中含有 null 元素的时候，该方法会导致意料之外的 NullPointerException。
- 修改了资源文件的结构。
- 完善了所有不可更改类的编辑方法中抛出 UnsupportedOperationException 的描述。
- 在 develop.cfg.checker 中添加了多个工具
  - develop.cfg.checker.LongConfigChecker
  - develop.cfg.checker.ShortConfigChecker
  - develop.cfg.checker.ByteConfigChecker
  - develop.cfg.checker.DoubleConfigChecker
  - develop.cfg.checker.FloatConfigChecker
  - develop.cfg.parser.ClassValueParser
- 在 develop.cfg.parser 中添加了多个工具
  - develop.cfg.parser.LongValueParser
  - develop.cfg.parser.ShortValueParser
  - develop.cfg.parser.ByteValueParser
  - develop.cfg.parser.FloatValueChecker
  - develop.cfg.parser.DoubleValueParser
- 新增 develop.resource.Url2RepoRresource 用以构建基于资源仓库的资源。
  - 新增 dutil.develop.resource.io.XmlJar2RepoResourceLoader 通过 XML 配置读取资源列表。
- 新增 develop.i18n.I18nHandler 功能，用于快速获取当前国际化接口中的文本字段
  - I18nHandler.getString(String)
  - I18nHandler.getString(Name)
  - I18nHandler.getStringOrDefault(String, String)
  - I18nHandler.getStringOrDefault(Name, String)
- 修改 develop.i18n.io.XmlPropResourceI18nLoader
  - 新增了对默认语言读取的支持。
- 修改 develop.i18n.io.XmlPropFileI18nLoader
  - 新增了对默认语言读取的支持。
- 修改 develop.i18n.AbstractI18nInfo
  - 现在支持值为 null 的键了。
- 改进 develop.resource 包。
  - 为 develop.resource.Resource 增加了自动复位的机制。
  - develop.resource.io 包中的读取器增加了自动复位的选项。
- 对 basic.io.FileUtil 进行更新
  - 对必要方法的入口参数进行非 null 判定。
  - 增加方法 createDirIfNotExists(File)，如果不存在指定的目录，则创建该目录。
- 对部分异常文本进行了修复。
- 对部分代码结构进行优化。
- 规范 Builder 方法。
  - basic.gui.swing.JMenuItemAction.Builder 中方法名称更改。
  - basic.prog.DefaultVersion.Builder 中方法更改。
- 为 develop.cfg.struct.ConfigChecker 添加了 equals 方法。
- 为 develop.cfg.struct.ValueParser 添加了 equals 方法。
- 更新 basic.gui.swing.MuaListModel<E>
  - 更方便的可继承结构
  - 模型的迭代器、子列表均能够正确的发送侦听通知。
- 废弃 basic.prog.VersionType 中不符合规范的 SNAPSHOTS，改为 SNAPSHOT。
- 废弃 basic.num.NumberValueFilter 类，使用 basic.prog.Filter<NumberValue>。
- 为 basic.num.Interval 添加了新的重载方法。
- 新增 basic.gui.swing.MuaComboBoxModel<E>，实现了 List<E> 的下拉菜单模型。
- 新增 basic.cna.AttributeComplex 类，用来解决 Java 方法中返回多个值的问题。
- 在 develop.backgr.BackgroundUtil 中新增方法
  - BackgroundUtil.newTaskFromRunnable(Runnable, Set<TaskObverser>)
  - newTaskFromCallable(Callable<?>)
  - newTaskFromCallable(Callable<?>, Set<TaskObverser>)
  - blockedTask(Task, Task[])
  - blockedTask(Task, Task[], Set<TaskObverser>)
- 新增 develop.backgr.ResultTask<T> 类，支持任务返回结果。
- 优化了 develop 工具中大部分 Loader 和 Saver 的结构。
- 为 develop.resource.io.XmlJar2FileResourceLoader 添加了新的构造器方法。
  - 允许对读取的资源执行不同的重置策略。
- 修复 basic.io.StringOutputStream 的两个严重 bug。
  - 会导致其构造器方法 charset 失效的 bug。
  - 会导致多种编码输出在 flush 之后输出乱码的 bug。
- 优化了 basic.io.IOUtil.trans(InputStream, OutputStream, int) 的算法。
- 新增 com.dwarfeng.dutil.basic.str.UUIDUtil
  - com.dwarfeng.dutil.basic.str.UUIDUtil.toDenseString(UUID)，实现了 UUID 36 位到 22 位的压缩算法。
- 新增 com.dwarfeng.dutil.basic.gui.swing.MappingTableModel，实现了实现 List 接口的基于注解配置的 TableModel。
- 更新了 README.md。

### Beta_0.2.1_20200304_build_A

- 针对 com.dwarfeng.dutil.basic.prog 包中的版本机制进行了一系列修改。
  - 过期 com.dwarfeng.dutil.basic.prog.VersionType.SNAPSHOT，因为预览版应该是版本的属性，而不是版本的类型。
  - 更新 com.dwarfeng.dutil.basic.prog.DefaultVersion，在构造器方法中添加了 snapshot 的属性，并对所有的预览版本输出
    _SNAPSHOT 的后缀。
- 修改 com.dwarfeng.dutil.develop.i18n.io.XmlPropResourceI18nLoader 的文档注释，修正几处容易引起误会的文档注释。
- 优化 com.dwarfeng.dutil.develop.i18n.io.XmlPropResourceI18nLoader 的代码结构。
- 优化 com.dwarfeng.dutil.develop.i18n.io.XmlPropFileI18nLoader 的代码结构。
- 优化 com.dwarfeng.dutil.develop.resource.io.XmlJar2FileResourceLoader 的代码结构。
- 优化 com.dwarfeng.dutil.basic.gui.swing.MappingTableModel 类代码，将注解的配置从维护对象转移到映射信息接口中。
- 新增 com.dwarfeng.dutil.develop.setting.info.DateSettingInfo， 为 SettingHandler 提供日期支持。
- 新增复用池功能包 com.dwarfeng.dutil.develop.reuse。
- 修改 .gitignore，使其适应 idea。
- 删除 dutil-assembly 模块。
- 优化 pom.xml 将各模块的版本号分离成属性。

### Beta_0.2.2_20220415_build_A

- 升级部分依赖版本，以消除其漏洞。

### Beta_0.3.0_20220622_build_A

- 修改观察器类错误的拼写，该变更是非兼容性变更。
- 修改计划类错误的拼写，该变更是非兼容性变更。
- 优化 MappingTableModel，使其能应对更多的场景。
- 依赖升级。
  - 升级 junit 依赖版本为 4.13.2 以规避漏洞。
- 变更日志样式更新，老变更日志重命名为 CHANGELOG.old。
