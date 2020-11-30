import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Timetable {
    private List<Record>table;
    public Timetable(){
        table = new ArrayList<>();
    }
    public void readData(String path) {
        try {
            FileReader fr = new FileReader(path + "input.txt");
            Scanner sc = new Scanner(fr);
            if (!sc.hasNextLine()){     //Проверяем, пустой ли файл
                System.out.println("input file is empty");
            }
            else {
                String[] data;
                while (sc.hasNextLine()) {
                    data = sc.nextLine().split(" ");
                    table.add(new Record(data[0], LocalTime.parse(data[1]), LocalTime.parse(data[2])));
                }
            }
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("input file is not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            System.out.println("wrong input format");
        }

    }
    public void modification(){     //выполняем все необходимые сортировки
        for (int i = 0; i < table.size(); ++i){     //Удаляем рейсы дольше часа
            if (table.get(i).isLongerThanHour()){
                table.remove(i);
                i--;
            }
        }
        sort(table, 0, table.size()-1);     //Сортируем рейсы по возрастанию времени отправления
        for (int i = 0; i < table.size() - 1; ++i){     //Удаляем рейсы Grotty если есть такой же рейс Posh
            if (table.get(i).isEqual(table.get(i + 1))){
                if (table.get(i).getCompany().equals("Grotty")){
                    table.remove(i);
                }
                if (table.get(i + 1).getCompany().equals("Grotty")){
                    table.remove(i + 1);
                }
                i--;
            }
        }
        for (int i = 0; i < table.size() - 1; ++i){     //Удаляем неэффективные рейсы
            if (table.get(i + 1).isMoreEfficient(table.get(i))){
                table.remove(i);
                i--;
            }
        }
    }
    public void writeData() {
        try {
        FileWriter fw = new FileWriter("output.txt");
        StringBuilder sectionPosh = new StringBuilder();
        StringBuilder sectionGrotty = new StringBuilder();
        for (Record r : table){
            if (r.getCompany().equals("Posh")){  //Формируем раздел рейсов Posh
                sectionPosh.append(r.toString());
                sectionPosh.append('\n');
            }
            if (r.getCompany().equals("Grotty")){   //Формируем раздел рейсов Grotty
                sectionGrotty.append('\n');
                sectionGrotty.append(r.toString());
            }
        }
        fw.write(sectionPosh.toString());
        fw.write(sectionGrotty.toString());
        fw.close();
        System.out.println("output.txt is ready");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sort(List<Record> list, int low, int high) {
        if (low < high) {
            int left = low + 1;
            int right = high;
            LocalTime pivotValue = list.get(low).getDeparture();
            while (left <= right) {
                while (left <= high && (pivotValue.isAfter(list.get(left).getDeparture())
                        ||pivotValue.equals(list.get(left).getDeparture()))) {
                    left++;
                }
                while (right > low && pivotValue.isBefore(list.get(right).getDeparture())) {
                    right--;
                }
                if (left < right) {
                    Collections.swap(list, left, right);
                }
            }
            Collections.swap(list, low, left - 1);
            sort(list, low, right - 1);
            sort(list, right + 1, high);
        }
    }
}
