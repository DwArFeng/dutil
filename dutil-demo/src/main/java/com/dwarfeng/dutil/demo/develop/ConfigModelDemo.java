package com.dwarfeng.dutil.demo.develop;

import com.dwarfeng.dutil.basic.io.StreamLoader;
import com.dwarfeng.dutil.develop.cfg.*;
import com.dwarfeng.dutil.develop.cfg.checker.IntegerConfigChecker;
import com.dwarfeng.dutil.develop.cfg.io.PropConfigLoader;
import com.dwarfeng.dutil.develop.cfg.obs.ConfigObserver;
import com.dwarfeng.dutil.develop.cfg.struct.ConfigChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * {@link ConfigModel} 的代码示例。
 *
 * <p>
 * 该示例将生成一个窗体，这个窗体的外观使用配置来记录。
 *
 * @author DwArFeng
 * @since 0.0.2-beta
 */
public class ConfigModelDemo {

    /**
     * 该配置是一个程序中，关于一个窗体的外观的配置。
     *
     * @author DwArFeng
     * @since 0.0.2-beta
     */
    private enum ApperanceConfig implements ConfigEntry {

        /**
         * 此配置字段表示窗体的高度，为 600；该配置值检查器是一个整型数检查器，取值在 1 到 3000 之间。
         * 也就是说，该配置值接受所有 1-3000 之间的整数。
         */
        APPEARANCE_HEIGHT("appearance.height", "600", new IntegerConfigChecker(1, 3000)),

        /**
         * 表示窗口的高度，同上。
         */
        APPEARANCE_WIDTH("appearance.width", "800", new IntegerConfigChecker(1, 3000)),

        ;

        private final String key;
        private final String defaultValue;
        private final ConfigChecker checker;
        private final ConfigFirmProps configFirmProps = new ConfigFirmProps() {

            /**
             * {@inheritDoc}
             */
            @Override
            public String getDefaultValue() {
                return defaultValue;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public ConfigChecker getConfigChecker() {
                return checker;
            }
        };

        ApperanceConfig(String key, String defaultValue, ConfigChecker checker) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.checker = checker;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigKey getConfigKey() {
            return new ConfigKey(key);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public ConfigFirmProps getConfigFirmProps() {
            return configFirmProps;
        }

    }

    private final class TestFrame extends JFrame {

        private static final long serialVersionUID = 1L;

        private final ConfigObserver observer = new ConfigObserver() {

            @Override
            public void fireCurrentValueChanged(ConfigKey configKey, String oldValue, String newValue,
                                                String validValue) {
                if (configKey.equals(ApperanceConfig.APPEARANCE_HEIGHT.getConfigKey())) {
                    setSize(getWidth(), Integer.parseInt(validValue));
                }
                if (configKey.equals(ApperanceConfig.APPEARANCE_WIDTH.getConfigKey())) {
                    setSize(Integer.parseInt(validValue), getHeight());
                }
            }

            @Override
            public void fireConfigKeyRemoved(ConfigKey configKey) {
            }

            @Override
            public void fireConfigKeyCleared() {
            }

            @Override
            public void fireConfigKeyAdded(ConfigKey configKey) {
            }

            @Override
            public void fireConfigFirmPropsChanged(ConfigKey configKey, ConfigFirmProps oldValue,
                                                   ConfigFirmProps newValue) {
            }
        };

        public TestFrame() {
            setTitle("ConfigModelDemo");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            applyConfig();
            addListener();
            addButton();
        }

        private void addButton() {
            Container content = getContentPane();
            content.setLayout(new FlowLayout());
            JButton jb1 = new JButton("Config 1");
            JButton jb2 = new JButton("Config 2");
            JButton jb3 = new JButton("Config 3");
            jb1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StreamLoader<CurrentValueContainer> loader = null;
                    try {
                        loader = new PropConfigLoader(this.getClass().getResourceAsStream(
                                "/com/dwarfeng/dutil/resources/demo/ConfigModelDemo_1.properties"));
                        loader.load(configModel);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        if (Objects.nonNull(loader)) {
                            try {
                                loader.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            jb2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StreamLoader<CurrentValueContainer> loader = null;
                    try {
                        loader = new PropConfigLoader(this.getClass().getResourceAsStream(
                                "/com/dwarfeng/dutil/resources/demo/ConfigModelDemo_2.properties"));
                        loader.load(configModel);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        if (Objects.nonNull(loader)) {
                            try {
                                loader.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            jb3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    StreamLoader<CurrentValueContainer> loader = null;
                    try {
                        loader = new PropConfigLoader(this.getClass().getResourceAsStream(
                                "/com/dwarfeng/dutil/resources/demo/ConfigModelDemo_3.properties"));
                        loader.load(configModel);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    } finally {
                        if (Objects.nonNull(loader)) {
                            try {
                                loader.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
            content.add(jb1);
            content.add(jb2);
            content.add(jb3);
        }

        private void addListener() {
            /**
             * 向配置模型中添加观察器，当配置发生变化时做出相应的响应。
             */
            configModel.addObserver(observer);
        }

        private void applyConfig() {
            /*
             * 这是典型的配置的应用方法
             */
            int width = Integer.parseInt(configModel.getValidValue(ApperanceConfig.APPEARANCE_WIDTH.getConfigKey()));
            int height = Integer.parseInt(configModel.getValidValue(ApperanceConfig.APPEARANCE_HEIGHT.getConfigKey()));
            setSize(width, height);
            setLocationRelativeTo(null);
        }

    }

    private final ConfigModel configModel;
    private final TestFrame testFrame;

    public ConfigModelDemo() {

        /*
         * 使用枚举定义配置入口的好处是可以快捷地将所有的配置以数组的形式传递到配置模型中。 使用枚举定义配置入口，枚举要实现 ConfigEntry
         * 接口。
         */
        this.configModel = new DefaultConfigModel(ApperanceConfig.values());

        /**
         * 显示界面。
         */
        this.testFrame = new TestFrame();
        testFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new ConfigModelDemo();
    }
}
