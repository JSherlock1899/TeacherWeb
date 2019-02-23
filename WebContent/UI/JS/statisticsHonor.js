/**
 * 专利的统计
 */

      $(function(){
    	   // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var json = $('#json').val();
        json = $.parseJSON( json ); 
        var keyArr = new Array();
        var valueArr = new Array();
        for(var key in json){
        	       keyArr.push(key);
        	       valueArr.push(json[key])
        	       
        	     }
        // 指定图表的配置项和数据
        var option = {
            title: {
                text: "各学院荣誉总数统计图"
            },
            tooltip: {},
            legend: {
                data:['荣誉数']
            },
            xAxis: {
                data: keyArr,
                axisLabel: {  
                	   interval:0,  
                	   rotate:30  
                	}  ,
                	 grid:{//直角坐标系内绘图网格
                         show:true,//是否显示直角坐标系网格。[ default: false ]
                         left:"20%",//grid 组件离容器左侧的距离。
                         right:"30px",
                         borderColor:"#c45455",//网格的边框颜色
                         bottom:"20%" //
                     },
            },
            yAxis: {},
            series: [{
                name: '荣誉数',
                type: 'bar',
                data: valueArr
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
      })
