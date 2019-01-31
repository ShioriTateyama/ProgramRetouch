package ec;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.BuyDataBeans;
import beans.ItemDataBeans;
import dao.BuyDAO;


/**
 * 購入履歴画面
 * @author d-yamaguchi
 *
 */
@WebServlet("/UserBuyHistoryDetail")
public class UserBuyHistoryDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		// セッション開始
		HttpSession session = request.getSession();
		//リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("buy_id");

		// 確認用：idをコンソールに出力
		System.out.println(id);
		int ID =Integer.parseInt(id);

		BuyDataBeans bdb= new BuyDataBeans();


		try {
			bdb = BuyDAO.getBuyDataBeansByBuyId(ID);
			request.setAttribute("buyDatabyBuyId", bdb);

			ArrayList<ItemDataBeans> detailList= dao.BuyDetailDAO.getItemDataBeansListByBuyId(ID);
			request.setAttribute("detailList", detailList);




			request.getRequestDispatcher(EcHelper.USER_BUY_HISTORY_DETAIL_PAGE).forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("errorMessage", e.toString());
			response.sendRedirect("Error");
		}



	}
}
