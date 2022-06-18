package lesson7;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseRepository {
    private String insertWeather = "insert into weather (city, localdate, temperature) values (?, ?, ?)";
    private String getInsertWeather = "select * from weather";
    private static final String DB_PATH = "jdbc:sqlite:geekbrains.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean saveWeatherToDataBase(Weather weather) throws SQLException {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            saveWeather.setString(1, weather.getCity());
            saveWeather.setString(2, weather.getLocalDate());
            saveWeather.setDouble(3, weather.getTemperature());
            return saveWeather.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new SQLException("Сохранение погоды в базу данных завершилось с ошибкой");
    }

    public void saveWeatherToDataBase(List<Weather> weatherList) {
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            PreparedStatement saveWeather = connection.prepareStatement(insertWeather);
            for (Weather weather : weatherList) {
                saveWeather.setString(1, weather.getCity());
                saveWeather.setString(2, weather.getLocalDate());
                saveWeather.setDouble(3, weather.getTemperature());
                saveWeather.addBatch();
            }
            saveWeather.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Weather> getSavedToDBWeather() {
        List<Weather> weatherList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DB_PATH)) {
            Statement statement = connection.createStatement();
            ResultSet weatherResult = statement.executeQuery (getInsertWeather);
            while (weatherResult.next()){
                System.out.print(weatherResult.getInt("id"));
                System.out.println();
                System.out.print(weatherResult.getString("city"));
                System.out.println();
                System.out.print(weatherResult.getString("localdate"));
                System.out.println();
                System.out.print(weatherResult.getDouble("temperature"));
                System.out.println();

                weatherList.add(new Weather(weatherResult.getString("city"),
                        weatherResult.getString("localdate"),
                        weatherResult.getDouble("temperature")));
            }
        }
        catch (
                SQLException e) {
            e.printStackTrace();
        }
        return weatherList;
    }

    public static void main(String[] args) throws SQLException {
        DataBaseRepository dataBaseRepository = new DataBaseRepository();
        dataBaseRepository.saveWeatherToDataBase(new Weather("Минск", "12.02.22", 12));

        dataBaseRepository.saveWeatherToDataBase(new ArrayList<>(Arrays.asList(
                new Weather("ttt1", "ttt1", 1),
                new Weather("ttt11", "ttt1", 1),
                new Weather("ttt111", "ttt1", 1),
                new Weather("ttt1111", "ttt1", 1))));
    }
}
