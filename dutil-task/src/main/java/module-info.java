module com.dwarfeng.dutil.task {

    requires com.dwarfeng.dutil.base;
    requires static org.jetbrains.annotations;

    exports com.dwarfeng.dutil.task.stack;
    exports com.dwarfeng.dutil.task.stack.event;
    exports com.dwarfeng.dutil.task.stack.executor;
    exports com.dwarfeng.dutil.task.stack.retry;
    exports com.dwarfeng.dutil.task.stack.scheduling;

    exports com.dwarfeng.dutil.task.sdk;
    exports com.dwarfeng.dutil.task.sdk.composition;
    exports com.dwarfeng.dutil.task.sdk.executor;
    exports com.dwarfeng.dutil.task.sdk.retry;
    exports com.dwarfeng.dutil.task.sdk.scheduling;

    exports com.dwarfeng.dutil.task.impl.executor;
    exports com.dwarfeng.dutil.task.impl.scheduling;

    opens com.dwarfeng.dutil.task.stack.i18n to com.dwarfeng.dutil.base;
    opens com.dwarfeng.dutil.task.impl.i18n to com.dwarfeng.dutil.base;
}
