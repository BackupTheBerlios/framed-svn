package net.sf.tacos.demo.partial;

import java.util.Iterator;
import java.util.List;

import org.apache.tapestry.IRequestCycle;
import org.apache.tapestry.html.BasePage;

public abstract class PartialCalls extends BasePage {

	public void onSubmit(IRequestCycle cycle) {
	}

	public void onFieldChange(IRequestCycle cycle) {
		try {
			Object[] params = cycle.getListenerParameters();
			int rowIdx = ((Long) params[0]).intValue();
			int colIdx = ((Long) params[1]).intValue();
			Integer value = Integer.valueOf((String) params[2]);

			List grid = getGridValues();
			List row = (List) grid.get(rowIdx);
			row.set(colIdx, value);
			setGridValues(grid);
		} catch (NumberFormatException e) {
		}
	}

	public int sum(List l) {
		int sum = 0;
		for (Iterator i = l.iterator(); i.hasNext();) {
			sum += ((Number) i.next()).intValue();
		}
		System.out.println("PartialCalls.sum(" + l + ") = " + sum);
		return sum;
	}

	public abstract List getGridValues();

	public abstract void setGridValues(List gridValues);

}
