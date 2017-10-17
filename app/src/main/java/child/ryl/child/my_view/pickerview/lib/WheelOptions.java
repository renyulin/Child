package child.ryl.child.my_view.pickerview.lib;

import android.view.View;

import java.util.ArrayList;

import child.ryl.child.R;

public class WheelOptions {
	private View view;
	private WheelView wv_option1;
	private WheelView wv_option2;
	private WheelView wv_option3;

	private ArrayList<String> mOptions1Items;
	private ArrayList<ArrayList<String>> mOptions2Items;
	private ArrayList<ArrayList<ArrayList<String>>> mOptions3Items;
	public int screenheight;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public WheelOptions(View view) {
		super();
		this.view = view;
		setView(view);
	}

	public void setPicker(ArrayList<String> optionsItems) {
		setPicker(optionsItems, null, null, false);
	}

	public void setPicker(ArrayList<String> options1Items,
						  ArrayList<ArrayList<String>> options2Items, boolean linkage) {
		setPicker(options1Items, options2Items, null, linkage);
	}

	public void setPicker(ArrayList<String> options1Items,
			ArrayList<ArrayList<String>> options2Items,
			ArrayList<ArrayList<ArrayList<String>>> options3Items,
			boolean linkage) {
		this.mOptions1Items = options1Items;
		this.mOptions2Items = options2Items;
		this.mOptions3Items = options3Items;
		int len = ArrayWheelAdapter.DEFAULT_LENGTH;
		if (this.mOptions3Items == null)
			len = 8;
		if (this.mOptions2Items == null)
			len = 12;
		// ѡ��1
		wv_option1 = (WheelView) view.findViewById(R.id.options1);
		wv_option1.setAdapter(new ArrayWheelAdapter(mOptions1Items, len));// ������ʾ���
		wv_option1.setCurrentItem(0);// ��ʼ��ʱ��ʾ�����
		// ѡ��2
		wv_option2 = (WheelView) view.findViewById(R.id.options2);
		if (mOptions2Items != null)
			wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items.get(0)));// ������ʾ���
		wv_option2.setCurrentItem(wv_option1.getCurrentItem());// ��ʼ��ʱ��ʾ�����
		// ѡ��3
		wv_option3 = (WheelView) view.findViewById(R.id.options3);
		if (mOptions3Items != null)
			wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items.get(0)
					.get(0)));// ������ʾ���
		wv_option3.setCurrentItem(wv_option3.getCurrentItem());// ��ʼ��ʱ��ʾ�����

		// �����Ļ�ܶ���ָ��ѡ��������Ĵ�С(��ͬ��Ļ���ܲ�ͬ)
		int textSize = (screenheight / 100) * 4;

		wv_option1.TEXT_SIZE = textSize;
		wv_option2.TEXT_SIZE = textSize;
		wv_option3.TEXT_SIZE = textSize;

		if (this.mOptions2Items == null)
			wv_option2.setVisibility(View.GONE);
		if (this.mOptions3Items == null)
			wv_option3.setVisibility(View.GONE);

		// ����������
		OnWheelChangedListener wheelListener_option1 = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mOptions2Items != null) {
					wv_option2.setAdapter(new ArrayWheelAdapter(mOptions2Items
							.get(wv_option1.getCurrentItem())));
					wv_option2.setCurrentItem(0);
				}
				if (mOptions3Items != null) {
					wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items
							.get(wv_option1.getCurrentItem()).get(
									wv_option2.getCurrentItem())));
					wv_option3.setCurrentItem(0);
				}
			}
		};
		OnWheelChangedListener wheelListener_option2 = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (mOptions3Items != null) {
					wv_option3.setAdapter(new ArrayWheelAdapter(mOptions3Items
							.get(wv_option1.getCurrentItem()).get(
									wv_option2.getCurrentItem())));
					wv_option3.setCurrentItem(0);
				}
			}
		};

		// �����������
		if (options2Items != null && linkage)
			wv_option1.addChangingListener(wheelListener_option1);
		if (options3Items != null && linkage)
			wv_option2.addChangingListener(wheelListener_option2);
	}

	/**
	 * ����ѡ��ĵ�λ
	 * 
	 * @param label1
	 * @param label2
	 * @param label3
	 */
	public void setLabels(String label1, String label2, String label3) {
		if (label1 != null)
			wv_option1.setLabel(label1);
		if (label2 != null)
			wv_option2.setLabel(label2);
		if (label3 != null)
			wv_option3.setLabel(label3);
	}

	/**
	 * �����Ƿ�ѭ������
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wv_option1.setCyclic(cyclic);
		wv_option2.setCyclic(cyclic);
		wv_option3.setCyclic(cyclic);
	}

	/**
	 * ���ص�ǰѡ�еĽ���Ӧ��λ������ ��Ϊ֧��������Ч��������������0��1��2
	 * 
	 * @return
	 */
	public int[] getCurrentItems() {
		int[] currentItems = new int[3];
		currentItems[0] = wv_option1.getCurrentItem();
		currentItems[1] = wv_option2.getCurrentItem();
		currentItems[2] = wv_option3.getCurrentItem();
		return currentItems;
	}

	public void setCurrentItems(int option1, int option2, int option3) {
		wv_option1.setCurrentItem(option1);
		wv_option2.setCurrentItem(option2);
		wv_option3.setCurrentItem(option3);
	}
}
