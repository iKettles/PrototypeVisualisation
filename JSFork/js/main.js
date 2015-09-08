var folderPrefix = "images/";
var dayData = [];
var temperaturePercentile = [27.72467809,27.75593641,27.78719472,27.81845304,27.84971135,27.89306898,27.9364266,27.97978423,28.02314185,28.0963341,28.16952635,28.2427186,28.31591085,28.37486042,28.43380998,28.55170911,28.66960823,28.78750736,28.79584632,28.80418528,28.81252423,28.82086319,28.97694013,29.13301706,29.289094,29.44517093,29.52733517,29.52733517,29.52733517,29.69166364,29.77382787,29.87284156,29.97185525,30.07086894,30.16988263,30.20073257,30.23158251,30.26243244,30.29328238,30.45205213,30.53143701,30.61082188,30.76959163,30.92836138,30.98023105,31.03210072,31.13584006,31.23957939,31.34331873,31.46305851,31.5827983,31.70253808,31.82227786,31.82965698,31.83703611,31.84441523,31.85179435,31.95367911,32.0046215,32.05556388,32.15744864,32.2593334,32.3161623,32.3729912,32.486649,32.6003068,32.7139646,32.88747591,33.06098723,33.23449854,33.40800985,33.42732461,33.44663936,33.46595412,33.48526887,33.77175836,33.91500311,34.05824785,34.34473734,34.63122683,34.68236491,34.73350298,34.78464106,34.83577913,34.88479763,34.93381614,34.98283464,35.03185314,35.05596156,35.08006998,35.1041784,35.12828682,35.2019816,35.27567639,35.34937117,35.42306595,35.61495843,35.8068509,35.99874338,36.19063585]
var humidityPercentile = [32.45291458,32.54172747,32.63054036,32.71935325,32.80816614,32.88043024,32.95269435,33.02495845,33.09722255,33.22686624,33.35650993,33.48615361,33.6157973,33.72631563,33.83683396,34.05787062,34.27890727,34.49994393,34.75181536,35.0036868,35.25555823,35.50742966,35.63566197,35.76389427,35.89212658,36.02035888,36.06697003,36.11358117,36.20680346,36.30002575,36.39324804,36.54861484,36.70398163,36.85934843,37.01471522,37.57044724,38.12617926,38.68191128,39.2376433,39.24813391,39.25862451,39.27960572,39.30058693,39.32156814,39.77386231,40.22615648,40.67845065,41.13074482,41.35156897,41.57239311,41.79321726,42.0140414,42.20235864,42.39067588,42.76731036,43.14394483,43.52057931,43.97788827,44.43519724,44.8925062,45.34981516,45.83114428,46.3124734,46.79380251,47.27513163,47.28114618,47.28716073,47.29918983,47.31121892,47.32324802,47.39144045,47.45963288,47.5278253,47.59601773,48.12804605,48.66007436,49.19210268,49.72413099,50.00642889,50.28872679,50.8533226,51.4179184,51.9825142,52.30400473,52.62549526,52.94698578,53.26847631,53.60291872,53.93736112,54.27180353,54.60624593,54.65881505,54.71138416,54.8165224,54.92166063,55.02679886,55.35230005,55.67780124,56.00330243,56.32880362]
var atmospherePool = {
  "hot" : "atmosphere/hot.png",
  "medium" : "atmosphere/medium.png",
  "low" : "atmosphere/low.png"
};
var backgroundPool = {
  "morning" : "backgrounds/morning.png",
  "afternoon" : "backgrounds/afternoon.png",
  "night" : "backgrounds/night.png"
};
var characterPool = {
  1 : {
    "prefix" : 1,
    "hot" : "hot.png",
    "medium" : "medium.png",
    "low" : "low.png"
  },
  2 : {
    "prefix" : 2,
    "hot" : "hot.png",
    "medium" : "medium.png",
    "low" : "low.png"
  },
  3 : {
    "prefix" : 3,
    "hot" : "hot.png",
    "medium" : "medium.png",
    "low" : "low.png"
  }
};

var thresholds = {
  "temperature" : {
    "low" : 24.2121777,
    "medium" : 29.47423465,
    "high" : 37.6923344
  },
  "humidity" : {
    "low" : 32.45291458,
    "medium" : 53.66699245,
    "high" : 75.01608663
  },
  "heatFactor" : {
    "low" : 33.33,
    "medium" : 66.66,
    "high" : 100
  }
};



function grabData(callback) {
  /* LIVE DATA FROM API
  $.get( "http://sensor-api.localdata.com/api/v1/aggregations?over.city=Bangalore&fields=temperature,light,humidity,airquality_raw,sound,dust&op=mean&from=2015-09-06T00:00:01Z&before=2015-09-08T23:59:59Z&resolution=60m", function( data ) {
    console.log(data);
  }); */

  // LOCAL JSON DAY DATA FILE
  $.getJSON( "js/daydata.json", function( data ) {
    dayData = data;
    callback(dayData);
  });

}

$(function() {
  grabData(function(data) {
    $.each( data, function( key, dataPoint ) {
      var tempPercentage = null;
      var humidityPecentage = null;
      for(var i = 0; i < temperaturePercentile.length; i++) {
        var result = calculatePercentile(dataPoint.temperature, i, temperaturePercentile[i], temperaturePercentile[i+1]);
        //console.log('Temperature result is '+result);
        if(result !== false) {
          tempPercentage = result;
          break;
        }
      }
      for(var index = 0; index < humidityPercentile.length; index++) {
        var result = calculatePercentile(dataPoint.humidity, index, humidityPercentile[index], humidityPercentile[index+1]);
        if(result !== false) {
          humidityPecentage = result;
          break;
        }
      }
      //console.log('Factor is '+((tempPercentage+humidityPecentage)/2));
      dataPoint.heatFactor = ((tempPercentage+humidityPecentage)/2);
    });
    console.log(dayData);
  });
});

function calculatePercentile(value, index, current, next) {
  if((value >= current && value < next) || value === current) {
    return index;
  } else {
    return false;
  }
}

