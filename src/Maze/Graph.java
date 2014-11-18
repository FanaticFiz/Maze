package Maze;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Graph
{
	static String[]				result;
	static ArrayList<Integer> 	path  	= new ArrayList<>();
	static boolean[] 			visited;
	static int width	= 10;
	static int heigth	= 10;
	
	/**
	 * Конечная точка в лабиринте
	 */
	static int to	= width*heigth-1;

	/**
	 * По умолчанию лабиринт 10 * 10
	 */
	public Graph() {
		result = new String[width*heigth];
		visited = new boolean[width*heigth];
		// Генерирует лабиринт в виде двухмерного массива.
		EllersAlgorithm ea = new EllersAlgorithm(width, heigth);
		Cell[][] cells = new Cell[heigth][width];
		for(int i = 0; i < heigth; i++) 		{	cells[i] = ea.step(i);	}
		for (int i = 0; i < result.length; i++) {	result[i] = "";	visited[i] = false;	}
		
		for (int i = 0; i < heigth; i++) {
			for (int j = 0; j < width; j++) {
				// Вниз есть путь?
				if (cells[i][j].isDown()) {
				}else {
					result[(width*i)+j] 		+= Integer.toString((width*i)+j+width)	+" ";
					result[(width*i)+j+width]	+= Integer.toString((width*i)+j)	+" ";
				}
				// Вправо есть путь?
				if (cells[i][j].isRight()) {				
				}else {
					result[(width*i)+j] 	+= Integer.toString((width*i)+j+1)	+" ";
					result[(width*i)+j+1]	+= Integer.toString((width*i)+j)	+" ";
				}
			}
		}
	}

	public Graph(int w, int h) {
		Graph.width  = w;
		Graph.heigth = h;
		result 	= new String[width*heigth];
		visited = new boolean[width*heigth];
		// Генерирует лабиринт в виде двухмерного массива.
		EllersAlgorithm ea = new EllersAlgorithm(width, heigth);
		Cell[][] cells = new Cell[heigth][width];
		for(int i = 0; i < heigth; i++) 		{	cells[i] = ea.step(i);	}
		for (int i = 0; i < result.length; i++) {	result[i] = "";	visited[i] = false;	}
		
		for (int i = 0; i < heigth; i++) {
			for (int j = 0; j < width; j++) {
				// Вниз есть путь?
				if (cells[i][j].isDown()) {
				}else {
					result[(width*i)+j] 		+= Integer.toString((width*i)+j+width)	+" ";
					result[(width*i)+j+width]	+= Integer.toString((width*i)+j)	+" ";
				}
				// Вправо есть путь?
				if (cells[i][j].isRight()) {				
				}else {
					result[(width*i)+j] 	+= Integer.toString((width*i)+j+1)	+" ";
					result[(width*i)+j+1]	+= Integer.toString((width*i)+j)	+" ";
				}
			}
		}
	}

	public static int 		getTo() 		{	return to;		}
	public static void 		setTo(int to) 	{
		if (to > width*heigth) {
			Graph.to = width*heigth-1;
		}else {
			Graph.to = to;
		}
	}
	
	public static ArrayList<Integer> getPath()	{	return path;	}
	
	public void PrintPath() {
		for (int i = 0; i < path.size(); i++) {
			System.out.print(path.get(i)+"  ");
		}
	}
	
	/**
	 * Выводит массив смежности.
	 */
	public void PrintGraph() {
		if (result.length == 0) {
			throw new RuntimeException("Массив не инициализирован"); 
		}else {
			for (int i = 0; i < result.length; i++) {
				System.out.println(i+". "+result[i]);
			}			
		}
	}
	
	/**
	 * Не работает как надо пока...
	 * @param from
	 */
	public void DFS(int from) {
		path.add(from);
	    visited[from] = true;
	    if (from != to) {
	    	String[] str = result[from].split(" ");
	    	for (int i = 0; i < str.length; i++) {
	    		int t = Integer.parseInt(str[i]);
	    		if (!visited[t])
	    			DFS(t);
	    	}
	    }else {
			for (int i = 0; i < visited.length; i++) {
				visited[i] = true;
			}
		} 
	}
    
/*	
	public ArrayList<String> Eller(int w, int h) {
		long startTime = System.currentTimeMillis();
		ArrayList<String>	result	= new ArrayList<>();	// Результирующий массив
		String[]	stroka	= new String[w*2];	
		String[] 	union	= new String[w*2];
		
		int l=0; 
		while (l<h) {

			for (int i = 0; i < w*2; i++) {
				union[i]  = "";
				stroka[i] = "";
			}
			
			// Построим граници между элементами в строке
			int j=0;
			for (int i = 0; i < w; i++) {
				int rand = (int) (Math.random() * 2);
				if (rand == 1) {
					// Строим связи с соседяи по строке
					union[j] += Integer.toString(i)+" ";
					if (i == w-1) {}
					else {
						stroka[i]   += Integer.toString(i+1+(w*l))+" ";
						stroka[i+1] += Integer.toString(i+(w*l))+" ";
					}
				}else {
					// Не обьединяем. Не строим никаких связей. Пишем конец обьединения
					union[j] += Integer.toString(i)+" ";
					j++;
				}
			}
			
			// Построим граници между строками
			for (int i = 0; i < union.length; i++) {
	    		String[] str = union[i].split(" ");
	    		if (str[0]=="") {
					
				}else {
		    		if (str.length == 1) {
		    			// Простой вариант. Единственный элемент во множестве. 
		    			// Границы нет, поэтому строим связь вниз
		    			int t = Integer.parseInt(str[0]);
		    			stroka[t] += Integer.toString(t+w+(w*l))+" ";
		    			stroka[t+w] += Integer.toString(t+w*l)+" ";
					}else {
						int rand2 = (int) (Math.random() * (str.length-1)); // случайно берем любой один элемент 
			    		for (int k = 0; k < str.length; k++) {
			    			int t = Integer.parseInt(str[k]);
			    			// для этого единственного элемента строим связь
			    			if (rand2 == k) {
			        			stroka[t] += Integer.toString(t+w+(w*l))+" ";
			        			stroka[t+w] += Integer.toString(t+w*l)+" ";
							}else {
								// для других элементов связей нет
							}
						}
					}
				}
			}
			

//			for (int i = 0; i < union.length; i++) {
//				if (union[i] != "") {
//					System.out.println(i+". "+union[i]);	
//				}
//			}
//			System.out.println("");
//			for (int i = 0; i < stroka.length; i++) {
//				System.out.println(i+". "+stroka[i]);
//			}
//			System.out.println("");
			
			// Запишем полученную строку в результирующий массив
			for (int i = 0; i < stroka.length; i++) {
				if (l == 0) {
					result.add(stroka[i]);
				}else if (l == h-1) {
					
				}else {
					int D = (stroka.length/2);
					if (i < D) {
						result.set(w*l+i, (result.get(w*l+i) + stroka[i]));
					}else {
						result.add(stroka[i]);
					}
				}
				
			}
		l++;
		}
		
		for (int i = 0; i < result.size(); i++) {
			System.out.print(result.get(i)+" | ");
			if ((i+1)%w == 0) {
				System.out.println("");
			}
		}
		long timeSpent = System.currentTimeMillis() - startTime;
		
		System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
		System.out.println("секунды : " + (float) timeSpent / 1000);
		return result;
	}
	

	public ArrayList<String> Eller2(int w, int h) {
		this.width 	= w;
		this.heigth = h;
		
		long startTime = System.currentTimeMillis();
		
		ArrayList<String> 	check	= new ArrayList<>();
		String[]	stroka	= new String[w*2];	
		int[] 		union	= new int[w*2];
		int[] 		union2	= new int[w*2];
		
		int l=0; 
		while (l<h) {
			
			// Началась новая строка, подготовим массив строки и обьединения.
			check.clear();
			for (int i = 0; i < w*2; i++) { 
				stroka[i] = "";
				if (i < stroka.length/2) 	{	check.add(Integer.toString(i));	}
				if (l == 0) 	{
					if (i < w) 	{	union[i] = i;	union2[i] = i;	}
					else 		{	union[i] = -1;	union2[i] = -1;	}
				}
			}
			
//			for (int i = 0; i < union.length; i++) {
//				if (i%w == 0) {
//					System.out.println("");
//				}
//				if (i < union.length/2) {
//					System.out.print(union[i]+" ");
//				}
//			}
			
			if (l == h-1) {
				// последняя строка
				for (int i = 0; i < w; i++) {
					if ((union[i] != union[i+1])&((i != w-1))) {
						// если два соседа различны 
						stroka[i]   += Integer.toString(i+1+(w*l))+" ";
						stroka[i+1] += Integer.toString(i+(w*l))+" ";
					}
				}
			}else {
				// Построим границы между элементами в строке
				int s1=0,s2=0;
				for (int i = 0; i < w; i++) {
					if ((union[i] == union[i+1])&((i != w-1))) {
						// если два елемента соседние у множества равны то обязательно строим границу
						s2=i;
						int kk = (s2 - s1)+1;		// Мы знаем длинну множества...
						if (kk <= 1) {			// На основе этого случайно определяем сколько стен хотим поставить
			    			stroka[s1] += Integer.toString(s2+w+(w*l))+" ";
			    			stroka[s1+w] += Integer.toString(s2+w*l)+" ";
			    			union2[s1+w] = union[s1];
			    			check.remove(Integer.toString(union[s1]));
						}else { 	
							//int r3 =	(int)((Math.random()*(kk-1))+1); // +1 чтобы исключить 0. kk-1=чтобы исключить kk
							int r3 =1;
							ArrayList<Integer> tt = RundomFromDiapazon(s1,s2,r3);
							for (int j = 0; j < tt.size(); j++) {
								stroka[tt.get(j)] += Integer.toString(tt.get(j)+w+(w*l))+" ";
								stroka[tt.get(j)+w] += Integer.toString(tt.get(j)+w*l)+" ";
								union2[tt.get(j)+w] = union[tt.get(j)];
				    			check.remove(Integer.toString(union[s1]));
							}
						}
						s1=i+1;
						
					}else {
						// Если не равны то мы будем случайно решать строить границу или нет
						if (new Random().nextBoolean()) {
							// Строим связи с соседями по строке
							if (i == w-1) {
								// вот тут уже будем строить нижние границы
								s2=i;
								int kk = (s2 - s1)+1;		// Мы знаем длинну множества...
								if (kk == 1) {			// На основе этого случайно определяем сколько стен хотим поставить
					    			stroka[s1] += Integer.toString(s2+w+(w*l))+" ";
					    			stroka[s1+w] += Integer.toString(s2+w*l)+" ";
					    			union2[s1+w] = union[s1];
					    			check.remove(Integer.toString(union[s1]));
								}else { 	// если во множестве более 1 ячейки, случайно возмем одну из них
									//int r3 =	(int)((Math.random()*(kk-1))+1);
									int r3 =1;
									ArrayList<Integer> tt = RundomFromDiapazon(s1,s2,r3);
									for (int j = 0; j < tt.size(); j++) {
										stroka[tt.get(j)] += Integer.toString(tt.get(j)+w+(w*l))+" ";
										stroka[tt.get(j)+w] += Integer.toString(tt.get(j)+w*l)+" ";
										union2[tt.get(j)+w] = union[tt.get(j)];
						    			check.remove(Integer.toString(union[s1]));
									}
								}
							}
							else {
								union2[i+1] = union[s1];
								stroka[i]   += Integer.toString(i+1+(w*l))+" ";
								stroka[i+1] += Integer.toString(i+(w*l))+" ";
							}
						}else {	
						// вот тут уже будем строить нижние границы
							s2=i;
							int kk = (s2 - s1)+1;		// Мы знаем длинну множества...
							if (kk <= 1) {			// На основе этого случайно определяем сколько стен хотим поставить
				    			stroka[s1] += Integer.toString(s2+w+(w*l))+" ";
				    			stroka[s1+w] += Integer.toString(s2+w*l)+" ";
				    			union2[s1+w] = union[s1];
				    			check.remove(Integer.toString(union[s1]));
							}else { 	
								//int r3 =	(int)((Math.random()*(kk-1))+1); // +1 чтобы исключить 0. kk-1=чтобы исключить kk
								int r3 =1;
								ArrayList<Integer> tt = RundomFromDiapazon(s1,s2,r3);
								for (int j = 0; j < tt.size(); j++) {
									stroka[tt.get(j)] += Integer.toString(tt.get(j)+w+(w*l))+" ";
									stroka[tt.get(j)+w] += Integer.toString(tt.get(j)+w*l)+" ";
									union2[tt.get(j)+w] = union[tt.get(j)];
					    			check.remove(Integer.toString(union[s1]));
								}
							}
							s1=i+1;
						}
					}
				}
			}
			
			
			// Запишем полученную строку в результирующий массив
			for (int i = 0; i < stroka.length; i++) {
				if (l == 0) {
					// если это первый проход просто копируем...
					result.add(stroka[i]);
				}else if (l == h-1) {
					// последняя строка
					if (i < stroka.length/2) {
						result.set(w*l+i, (result.get(w*l+i) + stroka[i]));
					}
				}else {
					// от второй до предпоследней строки
					if (i < stroka.length/2) {
						result.set(w*l+i, (result.get(w*l+i) + stroka[i]));
					}else {
						result.add(stroka[i]);
					}
				}
			}
			
			// Обьединение
			for (int i = 0; i < union2.length; i++) {
				if (i < union2.length/2) {
				}else {
					if (union2[i] < 0) {
						union2[i-w] 	= Integer.parseInt(check.get(0));
						check.remove(0);
					}
					union2[i] = -1;
				}
			}
			for (int i = 0; i < union2.length; i++) {
				union[i]=union2[i];
			}
			l++;
		}	
			
			
//		long timeSpent = System.currentTimeMillis() - startTime;
//		System.out.println("программа выполнялась " + timeSpent + " миллисекунд");
//		System.out.println("секунды : " + (float) timeSpent / 1000);
//		
//		System.out.print(0+"			"+1+"			"+2+"			"+3+"			"+4+"			");
//		for (int i = 0; i < result.size(); i++) {
//			if (i%w == 0) {
//				System.out.println("");
//			}
//			
//			System.out.print(result.get(i)+"			");
//		}
		
		return result;
	}

	/**
	 *Возвращает массив из count элементов, выбранных случайно из диапазона от d1 по d2
	 *(d2>d1) <br>
	 *10000 вызовов данной функции выполняется в среднем за 0,2 секунды (200милисекунд) 
	 * @return
	 */
	/*
	public ArrayList<Integer> RundomFromDiapazon(int d1, int d2, int count) {
		if ((d2>d1) & (((d2-d1)+1)>count)) {
			ArrayList<Integer> mas 	= new ArrayList<Integer>();
			ArrayList<Integer> mas2	= new ArrayList<Integer>();
			for (int i = d1; i <= d2; i++) {	mas.add(i);	}
			int k=1;
			while (k <= count) {
				int rand2 = (int)((Math.random()*(d2+1))+d1);
				for (int i = 0; i < mas.size(); i++) {
					if (mas.get(i) == rand2) {
						mas2.add(mas.get(i));
						mas.remove(i);
						k++;
						break;
					}
				}
			}
			return mas2;
		}else {
			throw new RuntimeException("Неверно заданы параметры");
		}
	}
	
	public void picture(int weight, int height) throws IOException {
		BufferedImage image = new BufferedImage(weight, height,	BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < weight; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y,	new Random().nextBoolean() ? Color.white.getRGB(): Color.black.getRGB());
			}
		}
		ImageIO.write(image, "jpg", new File("d:/randomCheck.jpg"));
	}
*/
	
	public void DrawMas() throws IOException {
		System.out.println();
		if (result.length != 0) {
			
			BufferedImage image = new BufferedImage(width*2+1, heigth*2+1,	BufferedImage.TYPE_INT_RGB);

			for (int x = 1,xx=0; x < heigth*2+1; x+=2,xx++) {
				for (int y = 1,yy=0; y < width*2+1; y+=2,yy++) {
					image.setRGB(y, x, Color.white.getRGB());
					
			    	String[] str = result[yy+(width*xx)].split(" ");
			    	for (int i = 0; i < str.length; i++) {
			    		int t = Integer.parseInt(str[i]);
			    		if (t == (yy+(width*xx)) + 1) {
							// сосед справа
			    			image.setRGB(y+1, x,	Color.white.getRGB());
						}else if (t == (yy+(width*xx)) + width) {
							// сосед снизу
			    			image.setRGB(y, x+1,	Color.white.getRGB());
						}else {
						}
			    	}
				}
			}
			
			ImageIO.write(image, "jpg", new File("d:/maze.jpg"));
		}else {
			throw new RuntimeException("Создайте лабиринт сначала...");
		}
	}

}
