package com.website.baseserver.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 * 天气
 * @author dream
 *
 */
@Entity
@Table(name="weather")
public class Weather implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name = "system-uuid",strategy="uuid")
	@Column(nullable = false, name = "id",length=32)
	private String id;//主键
	
	@Column(name="city_name",length=100)
	private String city_name;//城市
	
	@Column(name="cityid",length=10)
	private String cityid;//城市id
	
	@Column(name="citycode",length=20)
	private String citycode;//城市code
	
	@Column(name="date",length=25)
	private String date;//日期（yyyy-MM-dd）
	
	@Column(name="week",length=30)
	private String week;//星期
	
	@Column(name="weather",length=30)
	private String weather;//天气
	
	@Column(name="temp",length=30)
	private String temp;//气温
	
	@Column(name="temphigh",length=30)
	private String temphigh;//最高气温
	
	@Column(name="templow",length=30)
	private String templow;//最低气温
	
	@Column(name="img",length=30)
	private int img;//天气图片（对应图片数字）
	
	@Column(name="humidity",length=30)
	private String humidity;//湿度
	
	@Column(name="pressure",length=30)
	private String pressure;//气压
	
	@Column(name="windspeed",length=30)
	private String windspeed;//风速
	
	@Column(name="winddirect",length=30)
	private String winddirect;//风向
	
	@Column(name="windpower",length=30)
	private String windpower;//风级
	 
	@Column(name="updatetime",length=30)
	private String updatetime;//更新时间
	
	@Column(name="live_index",length=1000)
	private String live_index;//生活指数（json字符串集合iname:指数名称 ivalue:指数值 detail:指数详情）
	
	@Column(name="aqi",length=1000)
	private String aqi;//AQI指数（json字符串集合）
	
	@Column(name="daily",length=1000)
	private String daily;//天气情况（分白天，黑夜）（json字符串集合）
	
	@Column(name="hourly",length=1000)
	private String hourly;//天气情况（每小时为一段）（json字符串集合）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getTemphigh() {
		return temphigh;
	}

	public void setTemphigh(String temphigh) {
		this.temphigh = temphigh;
	}

	public String getTemplow() {
		return templow;
	}

	public void setTemplow(String templow) {
		this.templow = templow;
	}

	public int getImg() {
		return img;
	}

	public void setImg(int img) {
		this.img = img;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public String getWindspeed() {
		return windspeed;
	}

	public void setWindspeed(String windspeed) {
		this.windspeed = windspeed;
	}

	public String getWinddirect() {
		return winddirect;
	}

	public void setWinddirect(String winddirect) {
		this.winddirect = winddirect;
	}

	public String getWindpower() {
		return windpower;
	}

	public void setWindpower(String windpower) {
		this.windpower = windpower;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getLive_index() {
		return live_index;
	}

	public void setLive_index(String live_index) {
		this.live_index = live_index;
	}

	public String getAqi() {
		return aqi;
	}

	public void setAqi(String aqi) {
		this.aqi = aqi;
	}

	public String getDaily() {
		return daily;
	}

	public void setDaily(String daily) {
		this.daily = daily;
	}

	public String getHourly() {
		return hourly;
	}

	public void setHourly(String hourly) {
		this.hourly = hourly;
	}
	
	
}
