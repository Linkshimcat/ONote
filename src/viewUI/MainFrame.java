package viewUI;

import javax.swing.*;
import java.awt.*;
import model.*;
import service.*;
import ioCSV.*;
import java.awt.event.*;

public class MainFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel cardPanel = new JPanel(cardLayout);

    private WrongAnswerManager manager = new WrongAnswerManager();
    private CsvManager csv = new CsvManager();
    private ReviewScheduler scheduler = new ReviewScheduler();
    private MainPanel mainPanel;
    private ListPanel listPanel;
    private ReviewPanel reviewPanel;
    private SearchPanel searchPanel;
    private ArchivePanel archivePanel;

    public MainFrame() {
        for (WrongAnswer wa : csv.load()) {
            manager.add(wa);
        }
        int todayCount = scheduler.getTodayList(manager.getAll()).size();


        JFrame frame = new JFrame("ONote");
        frame.setSize(1200, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 16, 12, 16));


        JLabel logoLabel = new JLabel("오노트");
        ImageIcon raw = new ImageIcon("icons/OnoteLogo.png");
        Image scaled = raw.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));

        logoLabel.setFont(logoLabel.getFont().deriveFont(Font.BOLD, 25f));
        logoLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoLabel.setIconTextGap(8);
        logoLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mainPanel.refreshTable();
                cardLayout.show(cardPanel, "메인");
            }
        });

        JLabel countLabel = new JLabel("오늘 복습할 오답: " + todayCount + "개");

        headerPanel.add(logoLabel, BorderLayout.WEST);
        headerPanel.add(countLabel, BorderLayout.EAST);

        mainPanel = new MainPanel(manager, scheduler, csv, countLabel);
        listPanel = new ListPanel(manager, csv);
        reviewPanel = new ReviewPanel(manager, scheduler, countLabel);
        searchPanel = new SearchPanel(manager);
        archivePanel = new ArchivePanel(manager, scheduler);

        cardPanel.add(mainPanel, "메인");
        cardPanel.add(new RegisterPanel(manager), "등록");
        cardPanel.add(reviewPanel, "복습");
        cardPanel.add(listPanel, "목록");
        cardPanel.add(searchPanel, "검색");
        cardPanel.add(archivePanel, "보관함");


        JButton btnMain = new JButton("메인");
        JButton btnRegister = new JButton("오답 등록");
        JButton btnReview = new JButton("오늘 복습");
        JButton btnList = new JButton("전체 목록");
        JButton btnSearch = new JButton("검색");
        JButton btnArchive = new JButton("보관함");


        btnMain.addActionListener(e -> {
            mainPanel.refreshTable();
            cardLayout.show(cardPanel, "메인");
        });
        btnRegister.addActionListener(e -> cardLayout.show(cardPanel, "등록"));
        btnReview.addActionListener(e -> {
            reviewPanel.refreshTable();
            cardLayout.show(cardPanel, "복습");
        });
        btnList.addActionListener(e -> {
            listPanel.refreshTable();
            cardLayout.show(cardPanel, "목록");
        });
        btnSearch.addActionListener(e -> {
            searchPanel.search();
            cardLayout.show(cardPanel, "검색");
        });

        btnArchive.addActionListener(e -> {
            archivePanel.refreshTable();
            cardLayout.show(cardPanel, "보관함");
        });

        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 0, 8));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
        menuPanel.add(btnMain);
        menuPanel.add(btnList);
        menuPanel.add(btnRegister);
        menuPanel.add(btnReview);
        menuPanel.add(btnSearch);
        menuPanel.add(btnArchive);

        JPanel menuWrapper = new JPanel(new BorderLayout());
        menuWrapper.add(menuPanel, BorderLayout.NORTH);

        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(menuWrapper, BorderLayout.WEST);
        frame.add(cardPanel, BorderLayout.CENTER);


        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                csv.save(manager.getAll());
                System.out.println("프로그램 종료 (저장 완료)");
            }
        });

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                csv.save(manager.getAll());
            }
        });

        frame.setVisible(true);

        if (todayCount > 0) {
            JOptionPane.showMessageDialog(frame,
                    "오늘 복습할 오답이 " + todayCount + "개 있습니다.",
                    "복습 알림",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
