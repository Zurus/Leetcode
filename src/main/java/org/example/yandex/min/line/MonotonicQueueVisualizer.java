package org.example.yandex.min.line;

import javax.swing.*;
import javax.swing.Timer;
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

    // ---------- псевдокод ----------
    private static final String[] PSEUDOCODE = {
            "for i = 0 .. n-1:",
            "    while deque != 0 and a[deque.back] >= a[i]:",
            "        deque.popBack()",
            "    deque.pushBack(i)",
            "    if deque.front <= i - k:",
            "        deque.popFront()",
            "    if i >= k - 1:",
            "        answer.add(a[deque.front])"
    };
    private static final int PC_LOOP     = 0;
    private static final int PC_WHILE    = 1;
    private static final int PC_POPBACK  = 2;
    private static final int PC_PUSH     = 3;
    private static final int PC_IFPOP    = 4;
    private static final int PC_POPFRONT = 5;
    private static final int PC_IFANS    = 6;
    private static final int PC_ANS      = 7;

    private final int[] nums;
    private final int k;
    private final List<Step> steps;
    private int currentStep = 0;
    private Timer timer;

    private final ArrayPanel       arrayPanel;
    private final DequePanel       dequePanel;
    private final PseudocodePanel  pseudocodePanel;
    private final VariablesPanel   variablesPanel;
    private final AnswersPanel     answersPanel;
    private final JLabel           descriptionLabel;
    private final JLabel           stepCounterLabel;
    private final JButton          playButton;

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
        pseudocodePanel  = new PseudocodePanel();
        variablesPanel   = new VariablesPanel();
        answersPanel     = new AnswersPanel();

        descriptionLabel = new JLabel();
        descriptionLabel.setForeground(C_TEXT);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);

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

        // центр: массив + deque + (псевдокод | переменные) + ответы
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(C_BG);

        JPanel info = new JPanel(new GridLayout(1, 2, 6, 0));
        info.setBackground(C_BG);
        info.add(pseudocodePanel);
        info.add(variablesPanel);
        info.setMaximumSize(new Dimension(Integer.MAX_VALUE, 240));

        center.add(arrayPanel);
        center.add(dequePanel);
        center.add(info);
        center.add(answersPanel);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setBackground(C_PANEL);
        bottom.setBorder(new EmptyBorder(10, 14, 10, 14));
        bottom.add(descriptionLabel, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        setSize(1320, 900);
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
                "<html><body style='width:1240px; font-size:13px'>" +
                        s.description + "</body></html>");
        stepCounterLabel.setText("Шаг " + currentStep + " / " + (steps.size() - 1));
        arrayPanel.repaint();
        dequePanel.repaint();
        pseudocodePanel.repaint();
        variablesPanel.repaint();
        answersPanel.repaint();
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
            setPreferredSize(new Dimension(1000, 190));
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
            int startY = 78;

            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 15));
            g2.drawString("Массив", 20, 30);
            g2.setColor(C_DIM);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 12));
            g2.drawString("(k = " + k + ", i = " + s.currentI + ")", 90, 30);

            // легенда
            int lx = getWidth() - 640;
            int ly = 22;
            drawLegend(g2, lx,       ly, C_BOX_CURRENT, "текущий i");
            drawLegend(g2, lx + 130, ly, C_BOX_WINDOW,  "в окне");
            drawLegend(g2, lx + 240, ly, C_BOX_DEQUE,   "в deque");
            drawLegend(g2, lx + 350, ly, C_BOX_POP,     "удалён");
            drawLegend(g2, lx + 480, ly, C_BOX_ANSWER,  "ответ");

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

                g2.setColor(C_TEXT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 20));
                FontMetrics fm = g2.getFontMetrics();
                String val = String.valueOf(nums[i]);
                g2.drawString(val,
                        x + (BOX_W - fm.stringWidth(val)) / 2,
                        y + (BOX_H + fm.getAscent() - fm.getDescent()) / 2);

                g2.setFont(new Font("Monospaced", Font.PLAIN, 11));
                g2.setColor(C_DIM);
                fm = g2.getFontMetrics();
                String idx = String.valueOf(i);
                g2.drawString(idx, x + (BOX_W - fm.stringWidth(idx)) / 2,
                        y + BOX_H + 15);

                if (isPopped) {
                    g2.setColor(C_RED);
                    g2.setStroke(new BasicStroke(3));
                    int cx = x + BOX_W / 2;
                    int cy = y - 16;
                    g2.drawLine(cx - 7, cy - 7, cx + 7, cy + 7);
                    g2.drawLine(cx + 7, cy - 7, cx - 7, cy + 7);
                }

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

        private void drawLegend(Graphics2D g2, int x, int y, Color c, String label) {
            g2.setColor(c);
            g2.fillRoundRect(x, y - 12, 16, 16, 5, 5);
            g2.setColor(C_DIM);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString(label, x + 22, y + 1);
        }
    }

    // ================================================================
    //                     Панель с очередью
    // ================================================================

    private class DequePanel extends JPanel {
        private static final int BOX_W = 100;
        private static final int BOX_H = 55;
        private static final int GAP   = 14;

        DequePanel() {
            setBackground(C_BG);
            setPreferredSize(new Dimension(1000, 150));
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
            g2.drawString("(значения возрастают слева → направо, фронт = минимум окна)", 270, 30);

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
                boolean isTail = (i == s.dequeIdx.size() - 1);
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
                if (isHead && isTail) label = "голова = хвост";
                else if (isHead) label = "голова (min)";
                else if (isTail) label = "хвост";
                else label = "";
                if (!label.isEmpty()) {
                    g2.drawString(label,
                            x + (BOX_W - fm.stringWidth(label)) / 2,
                            y + BOX_H + 16);
                }

                // указатели head/tail под "сырым" массивом deque[]
                if (isHead) {
                    g2.setColor(C_GREEN);
                    g2.setFont(new Font("Monospaced", Font.BOLD, 11));
                    g2.drawString("head=" + s.headPtr, x + 2, y + BOX_H + 32);
                }
                if (isTail) {
                    g2.setColor(C_ORANGE);
                    g2.setFont(new Font("Monospaced", Font.BOLD, 11));
                    String t = "tail=" + s.tailPtr;
                    FontMetrics fm2 = g2.getFontMetrics();
                    g2.drawString(t, x + BOX_W - fm2.stringWidth(t) - 2, y + BOX_H + 32);
                }
            }
        }
    }

    // ================================================================
    //                     Псевдокод
    // ================================================================

    private class PseudocodePanel extends JPanel {
        PseudocodePanel() {
            setBackground(C_PANEL);
            setPreferredSize(new Dimension(600, 240));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Step s = steps.get(currentStep);

            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.drawString("Псевдокод (подсвечена текущая строка)", 15, 24);

            int y = 55;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
            for (int i = 0; i < PSEUDOCODE.length; i++) {
                boolean cur = (i == s.pseudoLine);
                if (cur) {
                    g2.setColor(new Color(70, 110, 160, 200));
                    g2.fillRoundRect(8, y - 15, getWidth() - 16, 20, 6, 6);
                }
                g2.setColor(cur ? Color.WHITE : C_DIM);
                g2.drawString(PSEUDOCODE[i], 15, y);
                y += 22;
            }
        }
    }

    // ================================================================
    //                     Переменные
    // ================================================================

    private class VariablesPanel extends JPanel {
        VariablesPanel() {
            setBackground(C_PANEL);
            setPreferredSize(new Dimension(600, 240));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Step s = steps.get(currentStep);

            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 14));
            g2.drawString("Переменные", 15, 24);

            String dequeVals = formatDequeValues(s);
            String headVal   = s.dequeIdx.isEmpty()
                    ? "—" : nums[s.dequeIdx.get(0)] + " (a[" + s.dequeIdx.get(0) + "])";
            String tailVal   = s.dequeIdx.isEmpty()
                    ? "—" : nums[s.dequeIdx.get(s.dequeIdx.size() - 1)]
                    + " (a[" + s.dequeIdx.get(s.dequeIdx.size() - 1) + "])";

            String[][] vars = {
                    {"n (длина массива)", String.valueOf(nums.length)},
                    {"k (размер окна)",   String.valueOf(k)},
                    {"i (текущий индекс)", s.currentI >= 0 ? String.valueOf(s.currentI) : "—"},
                    {"окно [L..R]",
                            (s.windowLeft >= 0)
                                    ? "[" + s.windowLeft + ".." + s.windowRight + "]"
                                    : "—"},
                    {"i − k", s.currentI >= 0 ? String.valueOf(s.currentI - k) : "—"},
                    {"head (сырой указатель)", String.valueOf(s.headPtr)},
                    {"tail (сырой указатель)", String.valueOf(s.tailPtr)},
                    {"|deque|", String.valueOf(s.dequeIdx.size())},
                    {"deque (индексы)", s.dequeIdx.toString()},
                    {"deque (значения)", dequeVals},
                    {"голова (min)", headVal},
                    {"хвост", tailVal},
            };

            int y = 55;
            g2.setFont(new Font("Monospaced", Font.PLAIN, 13));
            for (String[] v : vars) {
                g2.setColor(C_DIM);
                g2.drawString(v[0] + ":", 15, y);
                g2.setColor(C_ACCENT);
                g2.drawString(v[1], 260, y);
                y += 20;
            }
        }
    }

    private String formatDequeValues(Step s) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < s.dequeIdx.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(nums[s.dequeIdx.get(i)]);
        }
        sb.append("]");
        return sb.toString();
    }

    // ================================================================
    //                     Ответы (накопительный массив)
    // ================================================================

    private class AnswersPanel extends JPanel {
        AnswersPanel() {
            setBackground(C_PANEL);
            setPreferredSize(new Dimension(1000, 70));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            Step s = steps.get(currentStep);

            g2.setColor(C_TEXT);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString("Ответы (минимумы окон, по порядку):", 15, 40);

            int x = 320;
            int y = 18;
            int bw = 44, bh = 34, gap = 6;
            for (int i = 0; i < s.answers.size(); i++) {
                boolean latest = (i == s.answers.size() - 1);
                g2.setColor(latest ? C_BOX_ANSWER : C_BOX_DEQUE);
                g2.fillRoundRect(x, y, bw, bh, 8, 8);
                g2.setColor(latest ? C_GREEN : C_ORANGE);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(x, y, bw, bh, 8, 8);
                g2.setColor(C_TEXT);
                g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                String v = String.valueOf(s.answers.get(i));
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(v,
                        x + (bw - fm.stringWidth(v)) / 2,
                        y + (bh + fm.getAscent() - fm.getDescent()) / 2);
                x += bw + gap;
            }
            if (s.answers.isEmpty()) {
                g2.setColor(C_DIM);
                g2.setFont(new Font("SansSerif", Font.ITALIC, 12));
                g2.drawString("(пока пусто)", x, 40);
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
        int headPtr    = 0;
        int tailPtr    = 0;
        int pseudoLine = -1;
        List<Integer> answers = new ArrayList<>();
    }

    private static List<Step> buildSteps(int[] nums, int k) {
        int n = nums.length;
        List<Step> steps = new ArrayList<>();
        List<Integer> answers = new ArrayList<>();

        int[] deque = new int[n];
        int head = 0, tail = 0;

        Step init = new Step();
        init.description =
                "<b>Готовы к работе.</b> Нажмите «Шаг» или «Играть».<br>" +
                        "Идея алгоритма: в deque храним <b>индексы</b> так, чтобы значения " +
                        "<code>a[deque]</code> были строго возрастающими. Тогда " +
                        "<b>голова deque = минимум текущего окна</b>, а хвост всегда обновляется " +
                        "при добавлении нового элемента. Шаги: popBack → pushBack → popFront → ответ.";
        init.pseudoLine = -1;
        init.headPtr = 0;
        init.tailPtr = 0;
        init.answers = new ArrayList<>();
        steps.add(init);

        for (int i = 0; i < n; i++) {
            int winL = Math.max(0, i - k + 1);
            int winR = i;

            // --- начало итерации ---
            Step begin = new Step();
            begin.description = String.format(
                    "<b>Итерация i = %d.</b> Рассматриваем a[%d] = <b>%d</b>. " +
                            "Окно: [%d..%d], размер k = %d. " +
                            "Текущее состояние deque (индексы): %s.",
                    i, i, nums[i], winL, winR, k, active(deque, head, tail));
            begin.currentI     = i;
            begin.windowLeft   = winL;
            begin.windowRight  = winR;
            begin.dequeIdx     = active(deque, head, tail);
            begin.headPtr      = head;
            begin.tailPtr      = tail;
            begin.pseudoLine   = PC_LOOP;
            begin.answers      = new ArrayList<>(answers);
            steps.add(begin);

            // --- popBack ---
            while (tail > head && nums[deque[tail - 1]] >= nums[i]) {
                int removed = deque[tail - 1];
                tail--;
                Step s = new Step();
                s.description = String.format(
                        "<b>popBack.</b> Сравниваем хвост deque a[%d] = %d с текущим a[%d] = %d.<br>" +
                                "Условие: a[хвост] ≥ a[i]? → <b>%d ≥ %d = истина</b>. " +
                                "Индекс %d бесполезен: он правее i=%d не станет, и его значение не меньше нового. " +
                                "Убираем индекс %d с хвоста. Новый хвост: %s.",
                        removed, nums[removed], i, nums[i],
                        nums[removed], nums[i],
                        removed, i,
                        removed,
                        tail > head ? String.valueOf(deque[tail - 1]) : "(пусто)");
                s.currentI     = i;
                s.windowLeft   = winL;
                s.windowRight  = winR;
                s.dequeIdx     = active(deque, head, tail);
                s.headPtr      = head;
                s.tailPtr      = tail;
                s.justPopped   = removed;
                s.pseudoLine   = PC_POPBACK;
                s.answers      = new ArrayList<>(answers);
                steps.add(s);
            }

            // --- pushBack ---
            deque[tail++] = i;
            Step push = new Step();
            push.description = String.format(
                    "<b>pushBack(i = %d).</b> Добавляем индекс %d (a[%d] = %d) в хвост deque. " +
                            "Теперь deque (индексы): %s, значения: %s.",
                    i, i, i, nums[i],
                    active(deque, head, tail),
                    formatDequeValuesStatic(deque, head, tail, nums));
            push.currentI    = i;
            push.windowLeft  = winL;
            push.windowRight = winR;
            push.dequeIdx    = active(deque, head, tail);
            push.headPtr     = head;
            push.tailPtr     = tail;
            push.justPushed  = i;
            push.pseudoLine  = PC_PUSH;
            push.answers     = new ArrayList<>(answers);
            steps.add(push);

            // --- popFront ---
            if (deque[head] <= i - k) {
                int removed = deque[head];
                head++;
                Step s = new Step();
                s.description = String.format(
                        "<b>popFront.</b> Голова deque — индекс %d. " +
                                "Проверяем, вышла ли она за левую границу окна: " +
                                "%d ≤ i − k = %d − %d = %d? → <b>истина</b>.<br>" +
                                "Индекс %d больше не входит в окно [%d..%d]. Убираем его с головы. " +
                                "Новая голова: %s (a[%s] = %s).",
                        removed, removed, i, k, i - k, removed, winL, winR,
                        active(deque, head, tail).isEmpty()
                                ? "(пусто)"
                                : deque[head],
                        active(deque, head, tail).isEmpty()
                                ? "—"
                                : String.valueOf(deque[head]),
                        active(deque, head, tail).isEmpty()
                                ? "—"
                                : String.valueOf(nums[deque[head]]));
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.headPtr     = head;
                s.tailPtr     = tail;
                s.justPopped  = removed;
                s.pseudoLine  = PC_POPFRONT;
                s.answers     = new ArrayList<>(answers);
                steps.add(s);
            }

            // --- ответ ---
            if (i >= k - 1) {
                int minIdx = deque[head];
                answers.add(nums[minIdx]);
                Step s = new Step();
                s.description = String.format(
                        "<b>Ответ для окна [%d..%d]: min = a[%d] = %d.</b><br>" +
                                "Голова deque указывает на минимум: a[%d] = %d. " +
                                "Записываем %d в массив ответов (позиция %d). " +
                                "deque не теряет инвариант: значения возрастают от головы к хвосту, " +
                                "поэтому голова — минимум окна.",
                        winL, winR, minIdx, nums[minIdx],
                        minIdx, nums[minIdx],
                        nums[minIdx], answers.size() - 1);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.headPtr     = head;
                s.tailPtr     = tail;
                s.answerIdx   = minIdx;
                s.pseudoLine  = PC_ANS;
                s.answers     = new ArrayList<>(answers);
                steps.add(s);
            } else {
                Step s = new Step();
                s.description = String.format(
                        "Окно ещё не набрано: i = %d < k − 1 = %d. " +
                                "Ответ пока не выводим — ждём, пока в окне окажется ровно k элементов.",
                        i, k - 1);
                s.currentI    = i;
                s.windowLeft  = winL;
                s.windowRight = winR;
                s.dequeIdx    = active(deque, head, tail);
                s.headPtr     = head;
                s.tailPtr     = tail;
                s.pseudoLine  = PC_IFANS;
                s.answers     = new ArrayList<>(answers);
                steps.add(s);
            }
        }

        Step end = new Step();
        end.description = "Все окна обработаны. <b>Итоговый ответ:</b> " + answers +
                " (" + answers.size() + " значений = n − k + 1 = " +
                (nums.length - k + 1) + ").";
        end.pseudoLine = -1;
        end.dequeIdx   = active(deque, head, tail);
        end.headPtr    = head;
        end.tailPtr    = tail;
        end.answers    = new ArrayList<>(answers);
        steps.add(end);

        return steps;
    }

    private static String formatDequeValuesStatic(int[] deque, int head, int tail, int[] nums) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = head; i < tail; i++) {
            if (i > head) sb.append(", ");
            sb.append(nums[deque[i]]);
        }
        sb.append("]");
        return sb.toString();
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
            k = Integer.parseInt(args[1]);
            nums = new int[args.length - 2];
            for (int i = 2; i < args.length; i++) {
                nums[i - 2] = Integer.parseInt(args[i]);
            }
        } else {
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