package cn.com.leadfar.cms.backend.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.leadfar.cms.backend.dao.ArticleDao;
import cn.com.leadfar.cms.backend.model.Article;
import cn.com.leadfar.cms.backend.vo.PagerVO;

public class ArticleServlet extends BaseServlet {

	private ArticleDao articleDao;

	// �����������ִ�в�ѯ����
	@Override
	protected void execute(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int offset = 0;
		int pagesize = 5;

		// ϣ����request�л��offset����
		try {
			offset = Integer.parseInt(request.getParameter("pager.offset"));
		} catch (Exception ignore) {
		}

		// �����request���ݹ�����pagesize��������ô����Ҫ����http session�е�pagesize��ֵ
		if (request.getParameter("pagesize") != null) {
			request.getSession().setAttribute("pagesize",
					Integer.parseInt(request.getParameter("pagesize")));
		}

		// ϣ����http session�л��pagesize��ֵ�����û�У�������ȱʡֵΪ5
		Integer ps = (Integer) request.getSession().getAttribute("pagesize");
		if (ps == null) {
			request.getSession().setAttribute("pagesize", pagesize);
		} else {
			pagesize = ps;
		}

		// �ӽ����л�ȡtitle����
		String title = request.getParameter("title");

		PagerVO pv = articleDao.findArticles(title, offset, pagesize);

		request.setAttribute("pv", pv);

		// forward��article_list.jsp
		request.getRequestDispatcher("/backend/article/article_list.jsp")
				.forward(request, response);
	}

	public void add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ��request�л�ȡ����
		String title = request.getParameter("title");
		String source = request.getParameter("source");
		String content = request.getParameter("content");
		
		// �����ݲ������ݿ�
		Article a = new Article();
		a.setTitle(title);
		a.setSource(source);
		a.setContent(content);
		
		articleDao.addArticle(a);

		// forward���ɹ�ҳ��
		request
				.getRequestDispatcher(
						"/backend/article/add_article_success.jsp").forward(
						request, response);
	}
	
	//�򿪸��½���
	public void updateInput(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//���մӽ��洫�ݹ�����ID
		String id = request.getParameter("id");
		
		Article a = articleDao.findArticleById(Integer.parseInt(id));
		request.setAttribute("article", a);
		
		//forward������ҳ��
		request.getRequestDispatcher("/backend/article/update_article.jsp").forward(request, response);
	}

	//ִ�и��²���
	public void update(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		//���ȣ��ӽ���������µĻ�����Ϣ��������ID������ȵȣ�
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String source = request.getParameter("source");
		String content = request.getParameter("content");
		
		Article a = new Article();
		a.setId(Integer.parseInt(id));
		a.setTitle(title);
		a.setSource(source);
		a.setContent(content);
		
		articleDao.updateArticle(a);
		
		//forward�����³ɹ���ҳ��
		request.getRequestDispatcher("/backend/article/update_article_success.jsp").forward(request, response);
	}	
	
	//ִ��ɾ������
	public void del(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		//�ӽ������ID����
		String[] ids = request.getParameterValues("id");
		
		if(ids == null){
			//��ʾ����(forward������ҳ��)
			request.setAttribute("error", "�޷�ɾ�����£�ID������Ϊ��");
			request.getRequestDispatcher("/backend/common/error.jsp").forward(request, response);
			return;
		}
		articleDao.delArticles(ids);

		//ת���б�ҳ��
		//request.getRequestDispatcher("/backend/ArticleServlet").forward(request, response);
		response.sendRedirect(request.getContextPath()+"/backend/ArticleServlet");
	}	
	
	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

}