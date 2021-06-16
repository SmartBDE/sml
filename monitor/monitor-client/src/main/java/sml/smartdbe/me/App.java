package sml.smartdbe.me;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.pepstock.charba.client.colors.GoogleChartColor;
import org.pepstock.charba.client.colors.HtmlColor;
import org.pepstock.charba.client.colors.IsColor;
import org.pepstock.charba.client.configuration.CartesianCategoryAxis;
import org.pepstock.charba.client.configuration.CartesianLinearAxis;
import org.pepstock.charba.client.data.BarDataset;
import org.pepstock.charba.client.data.Dataset;
import org.pepstock.charba.client.data.Labels;
import org.pepstock.charba.client.data.LineDataset;
import org.pepstock.charba.client.enums.InteractionMode;
import org.pepstock.charba.client.gwt.widgets.BarChartWidget;
import org.pepstock.charba.client.gwt.widgets.LineChartWidget;
import org.pepstock.charba.client.resources.DeferredResources;
import org.pepstock.charba.client.resources.EmbeddedResources;
import org.pepstock.charba.client.resources.EntryPointStarter;
import org.pepstock.charba.client.resources.ResourcesType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class App implements EntryPoint {
    /**
     * Create a remote service proxy to talk to the server-side service.
     */
    private final StatJobCountServiceAsync statJobCountService = GWT.create(StatJobCountService.class);
    private final StatJobTimeServiceAsync statJobTimeService = GWT.create(StatJobTimeService.class);

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        // 如果要使用chart，这里是必须要调用的
        ResourcesType.setClientBundle(EmbeddedResources.INSTANCE);

        //-----------------------------------------
        // errorLabel 初始化
        //-----------------------------------------
        final Label errorLabel = new Label();
        RootPanel.get("errorLabelContainer").add(errorLabel);

        HelloService helloService = GWT.create(HelloService.class);
        helloService.order(
                new MethodCallback<String>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        errorLabel.setText("??");
                    }

                    @Override
                    public void onSuccess(Method method, String s) {
                        errorLabel.setText(s);
                    }
                }
            );

        //-----------------------------------------
        // chart 任务，执行次数。按最近若干次生成柱状图
        //-----------------------------------------
        statJobCountService.jobCount(
                new AsyncCallback<Map<String, Integer>>() {
                    public void onFailure(Throwable caught) {
                        errorLabel.setText("job count failed");
                    }

                    @Override
                    public void onSuccess(Map<String, Integer> map) {
                        BarChartWidget chart = new BarChartWidget();

                        chart.getOptions().setResponsive(true);
                        chart.getOptions().getTitle().setText("任务执行次数统计");

                        BarDataset dataset = chart.newDataset();
                        dataset.setLabel("任务列表");

                        dataset.setBackgroundColor(HtmlColor.CORNFLOWER_BLUE.alpha(0.2));
                        dataset.setBorderColor(HtmlColor.CORNFLOWER_BLUE);
                        dataset.setBorderWidth(1);

                        List<String> keys = new ArrayList<>();
                        List<Double> values = new ArrayList<>();

                        for (Map.Entry<String, Integer> entry : map.entrySet()) {
                            keys.add(entry.getKey());
                            values.add(new Double(entry.getValue()));
                        }

                        dataset.setData(values);
                        Labels labels = Labels.build();
                        labels.load(keys);
                        chart.getData().setLabels(labels);
                        chart.getData().setDatasets(dataset);

                        RootPanel.get("chartContainer").add(chart);
                    }
                });


        //-----------------------------------------
        // line 任务，会话，耗时，每一个任务都会生成一条曲线
        //-----------------------------------------
        statJobTimeService.jobTime(
                new AsyncCallback<Map<String, Map<String, Integer>>>() {
                    public void onFailure(Throwable caught) {
                        errorLabel.setText("job time failed");
                    }

                    @Override
                    public void onSuccess(Map<String, Map<String, Integer>> map) {
                        LineChartWidget chart = new LineChartWidget();

                        chart.getOptions().setResponsive(true);
                        chart.getOptions().setMaintainAspectRatio(true);
                        chart.getOptions().getLegend().setDisplay(true);
                        chart.getOptions().getTitle().setDisplay(true);
                        chart.getOptions().getTitle().setText("任务会话耗时统计");
                        chart.getOptions().getTooltips().setMode(InteractionMode.INDEX);
                        chart.getOptions().getTooltips().setIntersect(false);
                        chart.getOptions().getHover().setMode(InteractionMode.NEAREST);
                        chart.getOptions().getHover().setIntersect(true);

                        List<Dataset> datasets = chart.getData().getDatasets(true);
                        List<String> keys = new ArrayList<>();

                        int colorIndex = 0;
                        for (Map.Entry<String, Map<String, Integer>> entry : map.entrySet()) {
                            LineDataset dataset1 = chart.newDataset();
                            dataset1.setLabel(entry.getKey());
                            IsColor color1 = GoogleChartColor.values()[colorIndex];
                            dataset1.setBackgroundColor(color1.toHex());
                            dataset1.setBorderColor(color1.toHex());
                            dataset1.setFill(false);

                            Map<String, Integer> datas = entry.getValue();
                            double[] values = new double[datas.size()];
                            int dataIndex = 0;
                            for (Map.Entry<String, Integer> data : datas.entrySet()) {
                                values[dataIndex] = data.getValue();
                                keys.add(data.getKey());
                                dataIndex++;
                            }

                            List<Double> data = dataset1.getData(true);
                            for (int i = 0; i < values.length; i++) {
                                data.add(values[i]);
                            }
                            datasets.add(dataset1);

                            colorIndex = colorIndex++ % GoogleChartColor.values().length;
                        }

                        CartesianCategoryAxis axis1 = new CartesianCategoryAxis(chart);
                        axis1.setDisplay(true);
                        axis1.getScaleLabel().setDisplay(true);
                        axis1.getScaleLabel().setLabelString("会话");

                        CartesianLinearAxis axis2 = new CartesianLinearAxis(chart);
                        axis2.setDisplay(true);
                        axis2.getScaleLabel().setDisplay(true);
                        axis2.getScaleLabel().setLabelString("耗时");

                        chart.getOptions().getScales().setXAxes(axis1);
                        chart.getOptions().getScales().setYAxes(axis2);

                        Labels labels = Labels.build();
                        labels.load(keys);
                        chart.getData().setLabels(labels);

                        RootPanel.get("lineContainer").add(chart);
                    }
                });
    }
}
