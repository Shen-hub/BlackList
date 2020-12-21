package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<BlackList> bls = new ArrayList<BlackList>();

    public static void readBlackList(String file) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(file));
        String[] buf = new String[4];
        sc.useDelimiter("[;\\n]");
        while(sc.hasNextLine()){
            int i = 0;
            while (sc.hasNext() && i < 4)
            {
                buf[i] = sc.next();
                i++;
            }
            BlackList bl = new BlackList(buf[0], buf[1], buf[2], buf[3]);
            bls.add(bl);
        }
    }

    //1) Есть ли запрошенный URL в списке запрещенных
    public static String checkURl(String inURL)
    {
        for (BlackList bl : bls)
        {
            if (inURL.equals(bl.url))
            {
                return "Данный URL (" + inURL + ") содержится в запрещённом реестре.";
            }
        }
        return "Данный URL (" + inURL + ") не содержится в запрещённом реестре.";
    }

    //2) Извлечь домен из введенного пользователем URL и проверить его по списку (например, используя регулярные выражения);
    public static String checkDomain(String inURL)
    {
        String inDomain = inURL.split("https?://")[1].split("/.*")[0];
        for (BlackList bl : bls)
        {
            if (inDomain.equals(bl.domain))
            {
                return "Данный домен (" + inDomain + ") содержится в запрещённом реестре.";
            }
        }
        return "Данный домен (" + inDomain + ") не содержится в запрещённом реестре.";
    }

    //3) Определить (используя сетевые библиотеки) IP адрес введенного домена и проверить его по списку.
    public static String checkIP(String inURL) throws UnknownHostException {
        String inDomain = inURL.split("https?://")[1].split("/.*")[0];
        try {
            InetAddress inIP = InetAddress.getByName(inDomain);
            String IP = inIP.getHostAddress();
            for (BlackList bl : bls)
            {
                Scanner sc = new Scanner(bl.domain);
                sc.useDelimiter(",");
                while (sc.hasNext())
                {
                    if (IP.equals(sc.next()))
                    {
                        return "Данный IP (" + IP + ") содержится в запрещённом реестре.";
                    }
                }
            }
            return "Данный IP (" + IP + ") не содержится в запрещённом реестре.";
        }
        catch (IOException e)
        {
            return e.getMessage();
        }
    }

    public static void main(String[] args) throws FileNotFoundException, UnknownHostException {
        Scanner in = new Scanner(System.in);
        readBlackList("register.txt");
        System.out.println("Загрузка регистра завершена.");
        String URL;
        do {
            System.out.println("Введите URL: ");
            URL = in.next();
        } while (URL == "");

        System.out.println(checkURl(URL));
        System.out.println(checkDomain(URL));
        System.out.println(checkIP(URL));
        System.out.println("Конец работы.");
    }
}
