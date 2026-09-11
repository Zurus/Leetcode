package org.example.yandex.min.line;

import javax.swing.Timer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.*;




public class MonotonicQueueVisualizer extends JFrame {

    // ---------- цвета ----------
    private static final Color C_BG          = new Color(28, 30, 40);
    private static final Color C_PANEL       = new Color(40, 44, 58);
    private static final Color C_BOX         = new Color(64, 68, 88);
    private static final Color C_BOX_WINDOW  = new Color(48, 82, 60);
    private static final Color C_BOX_CURRENT = new Color(70, 110, 160);
    private static final Color C_BOX_DEQUE   = new Color(120, 88, 40);
    private static final Color C_BOX_POP     = new Color(130, 50, 50);
    private static final Color C_BOX_ANSWER  = new Color(50, 130, 80);
    private static final Color C_TEXT        = new Color(235, 235, 245);
    private static final Color C_DIM         = new Color(150, 150, 170);
    private static final Color C_ACCENT      = new Color(120, 200, 255);
    private static final Color C_ORANGE      = new Color(255, 170, 60);
    private static final Color C_GREEN       = new Color(90, 220, 130);
    private static final Color C_RED         = new Color(240, 90, 90);

    private final int[] nums;
    private final int k;
    private final List<Step> steps;
    private int currentStep = 0;
    private Timer timer;

    private final ArrayPanel arrayPanel;
    private final DequePanel dequePanel;
    private final JLabel descriptionLabel;
    private final JLabel stepCounterLabel;
    private final JButton playButton;

    public MonotonicQueueVisualizer(int[] nums, int k) {
        super("Минимум в скользящем окне — визуализация");
        this.nums = nums;
        this.k = k;
        this.steps = buildSteps(nums, k);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(C_BG);

        arrayPanel       = new ArrayPanel();
        dequePanel       = new DequePanel();
        descriptionLabel = new JLabel();
        descriptionLabel.setForeground(C_TEXT);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        stepCounterLabel = new JLabel();
        stepCounterLabel.setForeground(C_DIM);
        stepCounterLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));

        playButton = new JButton("▶ Играть");
        JButton stepButton  = new JButton("⏭ Шаг");
        JButton resetButton = new JButton("↺ Сброс");
        JSlider speed = new JSlider(80, 1500, 600);
        speed.setInverted(true);

        playButton.addActionListener(e -> togglePlay(speed));
        stepButton.addActionListener(e -> doStep());
        resetButton.addActionListener(e -> doReset());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        top.setBackground(C_PANEL);
        top.add(playButton);
        top.add(stepButton);
        top.add(resetButton);
        top.add(Box.createHorizontalStrut(20));
        top.add(new JLabel("Скорость:"));
        top.add(speed);
        top.add(Box.createHorizontalStrut(20));
        top.add(stepCounterLabel);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(C_BG);
        center.add(arrayPanel);
        center.add(dequePanel);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(C_PANEL);
        bottom.setBorder(new EmptyBorder(10, 14, 10, 14));
        bottom.add(descriptionLabel, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setSize(1100, 660);
        setLocationRelativeTo(null);
        refresh();
    }

    private void togglePlay(JSlider speed) {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            playButton.setText("▶ Играть");
            return;
        }
        if (currentStep >= steps.size() - 1) currentStep = 0;
        timer = new Timer(speed.getValue(), e -> {
            if (currentStep < steps.size() - 1) {
                currentStep++;
                refresh();
            } else {
                timer.stop();
                playButton.setText("▶ Играть");
            }
        });
        timer.start();
        playButton.setText("⏸ Пауза");
    }

    private void doStep() {
        if (timer != null) timer.stop();
        playButton.setText("▶ Играть");
        if (currentStep < steps.size() - 1) {
            currentStep++;
            refresh();
        }
    }

    private void doReset() {
        if (timer != null) timer.stop();
        playButton.setText("▶ Играть");
        currentStep = 0;
        refresh();
    }

    private void refresh() {
        Step s = steps.get(currentStep);
        descriptionLabel.setText(
                "<html><body style='width:900px'>" + s.description + "</body></html>");
        stepCounterLabel.setText("Шаг " + currentStep + " / " + (steps.size() - 1));
        arrayPanel.repaint();
        dequePanel.repaint();
    }

    // ================================================================
    //                     Панель с массивом
    // ================================================================

    private class ArrayPanel extends JPanel {
        private static final int BOX_W = 55;
        private static final int BOX_H = 55;
        private static final int GAP   = 6;

        ArrayPanel() {
            setBackground(C_BG);
            setPreferredSize(new Dimension(1000, 180));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Step s = steps.get(currentStep);
            Set<Integer> dequeSet = new HashSet<>(s.dequeIdx);

            int n = nums.length;
            int totalW = n * BOX_W + (n - 1) * GAP;
            int startX = Math.max(20, (getWidth() - totalW) / 2);
            int startY = 70;

            // заголовок
            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 15));
            g2.drawString("Массив", 20, 30);
            g2.setColor(C_DIM);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("(окно k=" + k + ")", 95, 30);

            for (int i = 0; i < n; i++) {
                int x = startX + i * (BOX_W + GAP);
                int y = startY;

                boolean inWindow  = i >= s.windowLeft && i <= s.windowRight;
                boolean isCurrent = i == s.currentI;
                boolean isDeque   = dequeSet.contains(i);
                boolean isPopped  = i == s.justPopped;
                boolean isPushed  = i == s.justPushed;
                boolean isAnswer  = i == s.answerIdx;

                Color bg = C_BOX;
                if (isPopped) bg = C_BOX_POP;
                else if (isAnswer) bg = C_BOX_ANSWER;
                else if (isCurrent) bg = C_BOX_CURRENT;
                else if (inWindow) bg = C_BOX_WINDOW;
                else if (isDeque) bg = C_BOX_DEQUE;

                g2.setColor(bg);
                g2.fillRoundRect(x, y, BOX_W, BOX_H, 12, 12);

                if (isPopped) {
                    g2.setColor(C_RED);
                    g2.setStroke(new BasicStroke(3));
                } else if (isPushed) {
                    g2.setColor(C_GREEN);
                    g2.setStroke(new BasicStroke(3));
                } else if (isAnswer) {
                    g2.setColor(C_GREEN);
                    g2.setStroke(new BasicStroke(3));
                } else if (isDeque) {
                    g2.setColor(C_ORANGE);
                    g2.setStroke(new BasicStroke(2));
                } else if (isCurrent) {
                    g2.setColor(C_ACCENT);
                    g2.setStroke(new BasicStroke(2));
                } else {
                    g2.setColor(new Color(100, 100, 120));
                    g2.setStroke(new BasicStroke(1));
                }
                g2.drawRoundRect(x, y, BOX_W, BOX_H, 12, 12);

                // значение
                g2.setColor(C_TEXT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                String val = String.valueOf(nums[i]);
                g2.drawString(val,
                        x + (BOX_W - fm.stringWidth(val)) / 2,
                        y + (BOX_H + fm.getAscent() - fm.getDescent()) / 2);

                // индекс
                g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
                g2.setColor(C_DIM);
                fm = g2.getFontMetrics();
                String idx = String.valueOf(i);
                g2.drawString(idx, x + (BOX_W - fm.stringWidth(idx)) / 2,
                        y + BOX_H + 15);

                // маркер: выброшен — красный крестик сверху
                if (isPopped) {
                    g2.setColor(C_RED);
                    g2.setStroke(new BasicStroke(3));
                    int cx = x + BOX_W / 2;
                    int cy = y - 16;
                    g2.drawLine(cx - 7, cy - 7, cx + 7, cy + 7);
                    g2.drawLine(cx + 7, cy - 7, cx - 7, cy + 7);
                }

                // маркер: в очереди — оранжевый треугольник сверху
                if (isDeque && !isPopped && !isPushed) {
                    g2.setColor(C_ORANGE);
                    int cx = x + BOX_W / 2;
                    int cy = y - 12;
                    g2.fillPolygon(
                            new int[]{cx - 6, cx + 6, cx},
                            new int[]{cy, cy, cy + 8},
                            3);
                }
            }
        }
    }

    // ================================================================
    //                     Панель с очередью
    // ================================================================

    private class DequePanel extends JPanel {
        private static final int BOX_W = 90;
        private static final int BOX_H = 55;
        private static final int GAP   = 12;

        DequePanel() {
            setBackground(C_BG);
            setPreferredSize(new Dimension(1000, 160));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Step s = steps.get(currentStep);

            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 15));
            g2.drawString("Монотонная очередь индексов", 20, 30);
            g2.setColor(C_DIM);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("(значения возрастают слева → направо)", 270, 30);

            int y = 60;
            int startX = 40;

            if (s.dequeIdx.isEmpty()) {
                g2.setColor(C_DIM);
                g2.setFont(new Font("SansSerif", Font.ITALIC, 14));
                g2.drawString("(пусто)", startX + 8, y + 32);
                return;
            }

            for (int i = 0; i < s.dequeIdx.size(); i++) {
                int idx = s.dequeIdx.get(i);
                int val = nums[idx];
                int x = startX + i * (BOX_W + GAP);

                boolean isHead = (i == 0);
                g2.setColor(isHead ? C_BOX_ANSWER : C_BOX_DEQUE);
                g2.fillRoundRect(x, y, BOX_W, BOX_H, 12, 12);

                g2.setColor(isHead ? C_GREEN : C_ORANGE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(x, y, BOX_W, BOX_H, 12, 12);

                g2.setColor(C_TEXT);
                g2.setFont(new Font("Monospaced", Font.BOLD, 16));
                String txt = idx + " = " + val;
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(txt,
                        x + (BOX_W - fm.stringWidth(txt)) / 2,
                        y + (BOX_H + fm.getAscent() - fm.getDescent()) / 2);

                g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
                g2.setColor(C_DIM);
                fm = g2.getFontMetrics();
                String label;
                if (isHead && s.dequeIdx.size() == 1) label = "голова = хвост";
                else if (isHead) label = "голова (min)";
                else if (i == s.dequeIdx.size() - 1) label = "хвост";
                else label = "";
                if (!label.isEmpty()) {
                    g2.drawString(label,
                            x + (BOX_W - fm.stringWidth(label)) / 2,
                            y + BOX_H + 16);
                }
            }
        }
    }

    // ================================================================
    //                     Логика шагов
    // ================================================================

    private static class Step {
        String description = "";
        int currentI    = -1;
        int windowLeft  = -1;
        int windowRight = -1;
        List<Integer> dequeIdx = new ArrayList<>();
        int justPopped = -1;
        int justPushed = -1;
        int answerIdx  = -1;
    }

    private static List<Step> buildSteps(int[] nums, int k) {
        int n = nums.length;
        List<Step> steps = new ArrayList<>();

        int[] deque = new int[n];
        int head = 0, tail = 0;

        Step init = new Step();
        init.description = "Готовы к работе. Нажмите «Шаг» или «Играть», чтобы начать.";
        steps.add(init);

        for (int i = 0; i < n; i++) {
            int winL = Math.max(0, i - k + 1);
            int winR = i;

            // "начало итерации"
            Step begin = new Step();
            begin.description = String.format(
                    "<b>Шаг i=%d.</b> Рассматриваем a[%d] = %d. "
                            + "Текущее окно: [%d..%d].",
                    i, i, nums[i], winL, winR);
            begin.currentI     = i;
            begin.windowLeft   = winL;
            begin.windowRight  = winR;
            begin.dequeIdx     = active(deque, head, tail);
            steps.add(begin);

            // popBack
            while (tail > head && nums[deque[tail - 1]] >= nums[i]) {
                int removed = deque[tail - 1];
                tail--;
                Step s = new Step();
                s.description = String.format(
                        "<b>popBack.</b> Хвост очереди: a[%d] = %d ≥ a[%d] = %d. "
                                + "Индекс %d бесполезен: он больше и стоит левее, "
                                + "значит, уже никогда не станет минимумом. "
                                + "Убираем с хвоста.",
                        removed, nums[removed], i, nums[i], removed);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.justPopped  = removed;
                steps.add(s);
            }

            // push
            deque[tail++] = i;
            Step push = new Step();
            push.description = String.format(
                    "<b>push.</b> Добавляем индекс %d (a[%d] = %d) в хвост очереди.",
                    i, i, nums[i]);
            push.currentI    = i;
            push.windowLeft  = winL;
            push.windowRight = winR;
            push.dequeIdx    = active(deque, head, tail);
            push.justPushed  = i;
            steps.add(push);

            // popFront
            if (deque[head] <= i - k) {
                int removed = deque[head];
                head++;
                Step s = new Step();
                s.description = String.format(
                        "<b>popFront.</b> Индекс %d вышел за левую границу окна "
                                + "(%d ≤ i − k = %d). Убираем с головы.",
                        removed, removed, i - k);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.justPopped  = removed;
                steps.add(s);
            }

            // ответ / ещё не готово
            if (i >= k - 1) {
                int minIdx = deque[head];
                Step s = new Step();
                s.description = String.format(
                        "<b>Ответ для окна [%d..%d]: min = a[%d] = %d.</b> "
                                + "Это голова очереди — минимальный элемент окна.",
                        winL, winR, minIdx, nums[minIdx]);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.answerIdx   = minIdx;
                steps.add(s);
            } else {
                Step s = new Step();
                s.description = String.format(
                        "Окно ещё не набрано (нужно i ≥ %d). Пока ответ не выводим.",
                        k - 1);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                steps.add(s);
            }
        }

        return steps;
    }

    private static List<Integer> active(int[] deque, int head, int tail) {
        List<Integer> list = new ArrayList<>();
        for (int i = head; i < tail; i++) list.add(deque[i]);
        return list;
    }

    // ================================================================
    //                     Точка входа
    // ================================================================

    public static void main(String[] args) throws IOException {
        int[] nums;
        int k;

        if (args.length >= 2) {
            // java ...MonotonicQueueVisualizer 7 3 1 3 2 4 5 3 1
            k = Integer.parseInt(args[1]);
            nums = new int[args.length - 2];
            for (int i = 2; i < args.length; i++) {
                nums[i - 2] = Integer.parseInt(args[i]);
            }
        } else {
            // stdin в формате задачи
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            nums = new int[n];
            int idx = 0;
            while (idx < n) {
                String line = br.readLine();
                if (line == null) break;
                st = new StringTokenizer(line);
                while (st.hasMoreTokens() && idx < n) {
                    nums[idx++] = Integer.parseInt(st.nextToken());
                }
            }
        }

        final int[] numsF = nums;
        final int kF = k;
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }
            new MonotonicQueueVisualizer(numsF, kF).setVisible(true);
        });
    }
}