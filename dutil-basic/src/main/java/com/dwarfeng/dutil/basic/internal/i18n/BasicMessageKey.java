package com.dwarfeng.dutil.basic.internal.i18n;

import static com.dwarfeng.dutil.basic.internal.i18n.BasicMessages.Catalog.*;

/**
 * 模块私有消息键及资源职责目录。
 *
 * <p>
 * 该枚举只服务于模块内部消息解析，不属于公共 API。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public enum BasicMessageKey {

    ABSTRACT_LIST_MODEL_OBSERVERS_REQUIRED(SDK, "abstract_list_model.observers_required"),
    ABSTRACT_MAP_MODEL_OBSERVERS_REQUIRED(SDK, "abstract_map_model.observers_required"),
    ABSTRACT_REFERENCE_MODEL_OBSERVERS_REQUIRED(SDK, "abstract_reference_model.observers_required"),
    ABSTRACT_SET_MODEL_OBSERVERS_REQUIRED(SDK, "abstract_set_model.observers_required"),
    ARRAY_FIRST_REQUIRED(SDK, "array.first_required"),
    ARRAY_SECOND_REQUIRED(SDK, "array.second_required"),
    ARRAY_TARGET_REQUIRED(SDK, "array.target_required"),
    ARRAY_REQUIRED(SDK, "array.required"),
    ARRAY_GENERATOR_REQUIRED(SDK, "array.generator_required"),
    ARRAY_COMPONENT_TYPE_REQUIRED(SDK, "array.component_type_required"),
    ARRAY_INSTANTIATION_FORBIDDEN(SDK, "array.instantiation_forbidden"),
    ATTRIBUTE_COMPLEX_OBJECTS_REQUIRED(STACK, "attribute_complex.objects_required"),
    ATTRIBUTE_COMPLEX_OBJECT_COUNT_NOT_EVEN(STACK, "attribute_complex.object_count_not_even"),
    ATTRIBUTE_COMPLEX_ODD_ELEMENT_REQUIRED(STACK, "attribute_complex.odd_element_required"),
    ATTRIBUTE_COMPLEX_ODD_ELEMENT_TYPE_INVALID(STACK, "attribute_complex.odd_element_type_invalid"),
    ATTRIBUTE_COMPLEX_DELEGATE_REQUIRED(STACK, "attribute_complex.delegate_required"),
    ATTRIBUTE_COMPLEX_DELEGATE_CONTAINS_NULL(STACK, "attribute_complex.delegate_contains_null"),
    ATTRIBUTE_COMPLEX_KEY_REQUIRED(STACK, "attribute_complex.key_required"),
    ATTRIBUTE_COMPLEX_KEY_NOT_FOUND(STACK, "attribute_complex.key_not_found"),
    ATTRIBUTE_COMPLEX_VALUE_TYPE_REQUIRED(STACK, "attribute_complex.value_type_required"),
    BIT_SOURCE_REQUIRED(SDK, "bit.source_required"),
    BIT_DESTINATION_REQUIRED(SDK, "bit.destination_required"),
    BIT_LONG_LENGTH_OUT_OF_RANGE(SDK, "bit.long_length_out_of_range"),
    BIT_DATA_TOO_SHORT(SDK, "bit.data_too_short"),
    BIT_FIRST_OPERAND_REQUIRED(SDK, "bit.first_operand_required"),
    BIT_SECOND_OPERAND_REQUIRED(SDK, "bit.second_operand_required"),
    BIT_OPERAND_LENGTH_MISMATCH(SDK, "bit.operand_length_mismatch"),
    BIT_INSTANTIATION_FORBIDDEN(SDK, "bit.instantiation_forbidden"),
    BIT_SOURCE_START_NEGATIVE(SDK, "bit.source_start_negative"),
    BIT_DESTINATION_START_NEGATIVE(SDK, "bit.destination_start_negative"),
    BIT_LENGTH_NEGATIVE(SDK, "bit.length_negative"),
    BIT_SOURCE_TOO_SHORT(SDK, "bit.source_too_short"),
    BIT_DESTINATION_TOO_SHORT(SDK, "bit.destination_too_short"),
    BIT_DATA_REQUIRED(SDK, "bit.data_required"),
    BIT_OFFSET_NEGATIVE(SDK, "bit.offset_negative"),
    BIT_INT_LENGTH_OUT_OF_RANGE(SDK, "bit.int_length_out_of_range"),
    BYTE_BUFFER_INPUT_STREAM_BUFFER_REQUIRED(IMPL, "byte_buffer_input_stream.buffer_required"),
    BYTE_BUFFER_OUTPUT_STREAM_BUFFER_REQUIRED(IMPL, "byte_buffer_output_stream.buffer_required"),
    CLASS_TYPE_REQUIRED(SDK, "class.type_required"),
    CLASS_OBJECT_REQUIRED(SDK, "class.object_required"),
    CLASS_PRIMITIVE_TYPE_UNRECOGNIZED(SDK, "class.primitive_type_unrecognized"),
    CLASS_INSTANTIATION_FORBIDDEN(SDK, "class.instantiation_forbidden"),
    COLLECTION_SET_REQUIRED(SDK, "collection.set_required"),
    COLLECTION_ELEMENT_REQUIRED(SDK, "collection.element_required"),
    COLLECTION_ITERATOR_REQUIRED(SDK, "collection.iterator_required"),
    COLLECTION_TARGET_REQUIRED(SDK, "collection.target_required"),
    COLLECTION_FIRST_ITERATOR_REQUIRED(SDK, "collection.first_iterator_required"),
    COLLECTION_SECOND_ITERATOR_REQUIRED(SDK, "collection.second_iterator_required"),
    COLLECTION_LIST_REQUIRED(SDK, "collection.list_required"),
    COLLECTION_COMPARATOR_REQUIRED(SDK, "collection.comparator_required"),
    COLLECTION_GENERATOR_REQUIRED(SDK, "collection.generator_required"),
    COLLECTION_LIST_ITERATOR_REQUIRED(SDK, "collection.list_iterator_required"),
    COLLECTION_KEY_GENERATOR_REQUIRED(SDK, "collection.key_generator_required"),
    COLLECTION_REQUIRED(SDK, "collection.required"),
    COLLECTION_VALUE_GENERATOR_REQUIRED(SDK, "collection.value_generator_required"),
    COLLECTION_ENTRY_REQUIRED(SDK, "collection.entry_required"),
    COLLECTION_SEARCHER_REQUIRED(SDK, "collection.searcher_required"),
    COLLECTION_INSTANTIATION_FORBIDDEN(SDK, "collection.instantiation_forbidden"),
    COLLECTION_CONTAINS_NULL(SDK, "collection.contains_null"),
    COLLECTION_MAP_REQUIRED(SDK, "collection.map_required"),
    COLLECTION_KEY_REQUIRED(SDK, "collection.key_required"),
    COLLECTION_SOURCE_MAP_REQUIRED(SDK, "collection.source_map_required"),
    COLLECTION_NOT_EMPTY(SDK, "collection.not_empty"),
    COLLECTION_ENUMERATION_REQUIRED(SDK, "collection.enumeration_required"),
    CT_MULTILINE_TEXT(SDK, "ct.multiline_text"),
    CT_OUTPUT_TYPE_REQUIRED(SDK, "ct.output_type_required"),
    CT_MULTILINE_TYPE_REQUIRED(SDK, "ct.multiline_type_required"),
    NAME_FILTER_NAME_REQUIRED(IMPL, "name_filter.name_required"),
    NAME_FILTER_DELEGATE_REQUIRED(IMPL, "name_filter.delegate_required"),
    NAME_VALUE_REQUIRED(IMPL, "name.value_required"),
    TAG_FILTER_DESCRIPTION_REQUIRED(IMPL, "tag_filter.description_required"),
    KEY_LIST_MODEL_COLLECTION_REQUIRED(IMPL, "key_list_model.collection_required"),
    KEY_SET_MODEL_COLLECTION_REQUIRED(IMPL, "key_set_model.collection_required"),
    LIST_MODEL_DELEGATE_REQUIRED(IMPL, "list_model.delegate_required"),
    LIST_MODEL_COLLECTION_REQUIRED(IMPL, "list_model.collection_required"),
    MAP_MODEL_DELEGATE_REQUIRED(IMPL, "map_model.delegate_required"),
    MAP_MODEL_MAP_REQUIRED(IMPL, "map_model.map_required"),
    MAP_MODEL_COLLECTION_REQUIRED(IMPL, "map_model.collection_required"),
    SET_MODEL_DELEGATE_REQUIRED(IMPL, "set_model.delegate_required"),
    SET_MODEL_COLLECTION_REQUIRED(IMPL, "set_model.collection_required"),
    TAG_NAME_REQUIRED(IMPL, "tag.name_required"),
    TAG_DESCRIPTION_REQUIRED(IMPL, "tag.description_required"),
    FILE_REQUIRED(SDK, "file.required"),
    FILE_SOURCE_REQUIRED(SDK, "file.source_required"),
    FILE_TARGET_REQUIRED(SDK, "file.target_required"),
    FILE_INSTANTIATION_FORBIDDEN(SDK, "file.instantiation_forbidden"),
    INTERVAL_LEFT_BOUNDARY_TYPE_REQUIRED(STACK, "interval.left_boundary_type_required"),
    INTERVAL_RIGHT_BOUNDARY_TYPE_REQUIRED(STACK, "interval.right_boundary_type_required"),
    INTERVAL_LEFT_GREATER_THAN_RIGHT(STACK, "interval.left_greater_than_right"),
    INTERVAL_ROUNDING_MODE_REQUIRED(STACK, "interval.rounding_mode_required"),
    INTERVAL_TEXT_REQUIRED(STACK, "interval.text_required"),
    INTERVAL_FORMAT_INVALID(STACK, "interval.format_invalid"),
    INTERVAL_VALUE_REQUIRED(STACK, "interval.value_required"),
    IO_INSTANTIATION_FORBIDDEN(SDK, "io.instantiation_forbidden"),
    ITERATOR_CHAIN_ITERATOR_REQUIRED(IMPL, "iterator_chain.iterator_required"),
    ITERATOR_CHAIN_ITERABLE_REQUIRED(IMPL, "iterator_chain.iterable_required"),
    ITERATOR_CHAIN_ARRAY_REQUIRED(IMPL, "iterator_chain.array_required"),
    LOCALE_SOURCE_REQUIRED(SDK, "locale.source_required"),
    LOCALE_SOURCE_INVALID(SDK, "locale.source_invalid"),
    MAP_KEY_SET_MODEL_MAP_REQUIRED(IMPL, "map_key_set_model.map_required"),
    MAP_KEY_SET_MODEL_COLLECTION_REQUIRED(IMPL, "map_key_set_model.collection_required"),
    MODEL_LIST_REQUIRED(SDK, "model.list_required"),
    MODEL_SET_REQUIRED(SDK, "model.set_required"),
    MODEL_MAP_REQUIRED(SDK, "model.map_required"),
    MODEL_KEY_LIST_REQUIRED(SDK, "model.key_list_required"),
    MODEL_KEY_SET_REQUIRED(SDK, "model.key_set_required"),
    MODEL_GENERATOR_REQUIRED(SDK, "model.generator_required"),
    MODEL_KEY_GENERATOR_REQUIRED(SDK, "model.key_generator_required"),
    MODEL_VALUE_GENERATOR_REQUIRED(SDK, "model.value_generator_required"),
    MODEL_REFERENCE_REQUIRED(SDK, "model.reference_required"),
    MODEL_INSTANTIATION_FORBIDDEN(SDK, "model.instantiation_forbidden"),
    MODEL_OBSERVER_NOTIFICATION_FAILED(SDK, "model.observer_notification_failed"),
    NUMBER_INT_VALUES_REQUIRED(SDK, "number.int_values_required"),
    NUMBER_DOUBLE_VALUES_REQUIRED(SDK, "number.double_values_required"),
    NUMBER_INTERVAL_REQUIRED(SDK, "number.interval_required"),
    NUMBER_INSTANTIATION_FORBIDDEN(SDK, "number.instantiation_forbidden"),
    NAME_COMPARATOR_OPERANDS_REQUIRED(SDK, "name_comparator.operands_required"),
    NUMBERED_THREAD_FACTORY_PREFIX_REQUIRED(SDK, "numbered_thread_factory.prefix_required"),
    PLUGIN_LOADER_DIRECTORY_REQUIRED(IMPL, "plugin_loader.directory_required"),
    PLUGIN_LOADER_CLOSED(IMPL, "plugin_loader.closed"),
    PLUGIN_LOADER_SERVICE_TYPE_REQUIRED(IMPL, "plugin_loader.service_type_required"),
    STREAM_LOADER_INPUT_REQUIRED(IMPL, "stream_loader.input_required"),
    STREAM_SAVER_OUTPUT_REQUIRED(IMPL, "stream_saver.output_required"),
    STRING_INPUT_STREAM_TEXT_REQUIRED(IMPL, "string_input_stream.text_required"),
    STRING_INPUT_STREAM_CHARSET_REQUIRED(IMPL, "string_input_stream.charset_required"),
    STRING_INPUT_STREAM_MARK_INVALID(IMPL, "string_input_stream.mark_invalid"),
    STRING_INPUT_STREAM_BUFFER_REQUIRED(IMPL, "string_input_stream.buffer_required"),
    STRING_INPUT_STREAM_BUFFER_RANGE_INVALID(IMPL, "string_input_stream.buffer_range_invalid"),
    STRING_OUTPUT_STREAM_CHARSET_REQUIRED(IMPL, "string_output_stream.charset_required"),
    STRING_INSTANTIATION_FORBIDDEN(SDK, "string.instantiation_forbidden"),
    SYNC_INPUT_STREAM_DELEGATE_REQUIRED(IMPL, "sync_input_stream.delegate_required"),
    SYNC_OUTPUT_STREAM_DELEGATE_REQUIRED(IMPL, "sync_output_stream.delegate_required"),
    TAG_RUNNABLE_DELEGATE_REQUIRED(IMPL, "tag_runnable.delegate_required"),
    TAG_RUNNABLE_TAG_REQUIRED(IMPL, "tag_runnable.tag_required"),
    THREAD_LOCK_REQUIRED(SDK, "thread.lock_required"),
    THREAD_INSTANTIATION_FORBIDDEN(SDK, "thread.instantiation_forbidden"),
    THREAD_OBJECT_REQUIRED(SDK, "thread.object_required"),
    TIME_MEASURER_START_STATE_INVALID(SDK, "time_measurer.start_state_invalid"),
    TIME_MEASURER_NOT_RUNNING(SDK, "time_measurer.not_running"),
    TIME_MEASURER_NOT_STOPPED(SDK, "time_measurer.not_stopped"),
    TIME_MEASURER_TOTAL_NANOSECONDS(SDK, "time_measurer.total_nanoseconds"),
    TIME_MEASURER_TOTAL_MILLISECONDS(SDK, "time_measurer.total_milliseconds"),
    TIME_MEASURER_TOTAL_SECONDS(SDK, "time_measurer.total_seconds"),
    TIME_DATE_REQUIRED(SDK, "time.date_required"),
    TIME_INSTANT_REQUIRED(SDK, "time.instant_required"),
    TIME_FIRST_DATE_REQUIRED(SDK, "time.first_date_required"),
    TIME_SECOND_DATE_REQUIRED(SDK, "time.second_date_required"),
    TIME_NANO_OFFSET_INVALID(SDK, "time.nano_offset_invalid"),
    TO_STRING_COMPARATOR_OPERANDS_REQUIRED(SDK, "to_string_comparator.operands_required"),
    UUID_INSTANTIATION_FORBIDDEN(SDK, "uuid.instantiation_forbidden"),
    UUID_VALUE_REQUIRED(SDK, "uuid.value_required");

    private final BasicMessages.Catalog catalog;
    private final String key;

    BasicMessageKey(BasicMessages.Catalog catalog, String key) {
        this.catalog = catalog;
        this.key = key;
    }

    /**
     * 返回消息资源所属的职责目录。
     *
     * @return 消息资源所属的职责目录。
     */
    BasicMessages.Catalog catalog() {
        return catalog;
    }

    /**
     * 返回语义化消息键。
     *
     * @return 消息键。
     */
    public String key() {
        return key;
    }

}
