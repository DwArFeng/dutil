module com.dwarfeng.dutil.basic {

    requires com.dwarfeng.dutil.base;
    requires org.slf4j;
    requires static org.jetbrains.annotations;

    exports com.dwarfeng.dutil.basic.stack.builder;
    exports com.dwarfeng.dutil.basic.stack.collection;
    exports com.dwarfeng.dutil.basic.stack.collection.tree;
    exports com.dwarfeng.dutil.basic.stack.concurrent;
    exports com.dwarfeng.dutil.basic.stack.function;
    exports com.dwarfeng.dutil.basic.stack.io;
    exports com.dwarfeng.dutil.basic.stack.lifecycle;
    exports com.dwarfeng.dutil.basic.stack.model;
    exports com.dwarfeng.dutil.basic.stack.model.event;
    exports com.dwarfeng.dutil.basic.stack.number;
    exports com.dwarfeng.dutil.basic.stack.number.unit;
    exports com.dwarfeng.dutil.basic.stack.plugin;
    exports com.dwarfeng.dutil.basic.stack.string;
    exports com.dwarfeng.dutil.basic.stack.version;

    exports com.dwarfeng.dutil.basic.sdk.bit;
    exports com.dwarfeng.dutil.basic.sdk.collection;
    exports com.dwarfeng.dutil.basic.sdk.concurrent;
    exports com.dwarfeng.dutil.basic.sdk.io;
    exports com.dwarfeng.dutil.basic.sdk.locale;
    exports com.dwarfeng.dutil.basic.sdk.model;
    exports com.dwarfeng.dutil.basic.sdk.model.event;
    exports com.dwarfeng.dutil.basic.sdk.number;
    exports com.dwarfeng.dutil.basic.sdk.reflect;
    exports com.dwarfeng.dutil.basic.sdk.string;
    exports com.dwarfeng.dutil.basic.sdk.time;

    exports com.dwarfeng.dutil.basic.impl.collection;
    exports com.dwarfeng.dutil.basic.impl.collection.tree;
    exports com.dwarfeng.dutil.basic.impl.concurrent;
    exports com.dwarfeng.dutil.basic.impl.io;
    exports com.dwarfeng.dutil.basic.impl.model;
    exports com.dwarfeng.dutil.basic.impl.number;
    exports com.dwarfeng.dutil.basic.impl.string;
    exports com.dwarfeng.dutil.basic.impl.version;

    opens com.dwarfeng.dutil.basic.stack.i18n to com.dwarfeng.dutil.base;
    opens com.dwarfeng.dutil.basic.sdk.i18n to com.dwarfeng.dutil.base;
    opens com.dwarfeng.dutil.basic.impl.i18n to com.dwarfeng.dutil.base;

    uses com.dwarfeng.dutil.basic.stack.plugin.Plugin;
}
