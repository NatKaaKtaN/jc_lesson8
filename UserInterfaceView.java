package lesson7;

import java.io.IOException;
        import java.util.Scanner;



public class UserInterfaceView {
    private Controller controller = new Controller();

    public void runInterface() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите имя города: ");
            String city = scanner.nextLine();

            System.out.println("Введите 1 для получения погоды на сегодня;" + "Введите 2 для получения погоды из базы данных" + " Введите 5 для прогноза на 5 дней; Для выхода введите 0:");
            String command = scanner.nextLine();


            if (command.equals("0")) break;

            if ((command.equals("1")) | (command.equals("5"))) {
                    try {
                        controller.getWeather(command, city);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                System.out.println("Введите допустимое количество дней");
            }

            //TODO* Сделать метод валидации пользовательского ввода

        }

    }
}
