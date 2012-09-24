package org.wangliang.app.learn.graph;

import java.util.Random ;

import org.achartengine.ChartFactory ;
import org.achartengine.chart.PointStyle ;
import org.achartengine.model.XYMultipleSeriesDataset ;
import org.achartengine.model.XYSeries ;
import org.achartengine.renderer.XYMultipleSeriesRenderer ;
import org.achartengine.renderer.XYSeriesRenderer ;

import android.app.Activity ;
import android.content.Intent ;
import android.graphics.Color ;
import android.os.Bundle ;

//从图中,我们可以看出,绘制一个图表我们其实,我们只需要理解三个概念
//http://www.cnblogs.com/youxilua/archive/2012/01/18/2326044.html

//1,ChartFactory ,传入XYMutilpleSeriesRenderer,XYMutilpleSeriesDataset,然后,我们只需用getXXXChartIntent(Context context,XYMutilpleSeriesRenderer,XYMutilpleSeriesDataset,)方法就可以进行图表的显示
//2,XYMutilpleSeriesRenderer 用于进行绘制的设置,添加的XYSeriesRender对象,用于定义绘制的点集合设置,注意数量要和XYMutilpleSeriesDataset,添加的XYseries一致!!!
//3,XYMutilpleSeriesDataset 用于数据的存放,添加的XYseries对象,用于提供绘制的点集合的数据

public class ChartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // 1, 构造显示用渲染图
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        // 2,进行显示
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // 2.1, 构建数据
        Random r = new Random();
        for (int i = 0; i < 2; i++) {
            XYSeries series = new XYSeries("test" + (i + 1));
            // 填充数据
            for (int k = 0; k < 10; k++) {
                // 填x,y值
                series.add(k, 20 + r.nextInt() % 100);
            }
            // 需要绘制的点放进dataset中
            dataset.addSeries(series);
        }
        // 3, 对点的绘制进行设置
        XYSeriesRenderer xyRenderer = new XYSeriesRenderer();
        // 3.1设置颜色
        xyRenderer.setColor(Color.BLUE);
        // 3.2设置点的样式
        xyRenderer.setPointStyle(PointStyle.SQUARE);
        // 3.3, 将要绘制的点添加到坐标绘制中
        renderer.addSeriesRenderer(xyRenderer);
        // 3.4,重复 1~3的步骤绘制第二个系列点
        xyRenderer = new XYSeriesRenderer();
        xyRenderer.setColor(Color.RED);
        xyRenderer.setPointStyle(PointStyle.CIRCLE);
        renderer.addSeriesRenderer(xyRenderer);
 
        // Intent intent = new LinChart().execute(this);
        Intent intent = ChartFactory
                .getLineChartIntent(this, dataset, renderer);
        startActivity(intent);
 
    }
}