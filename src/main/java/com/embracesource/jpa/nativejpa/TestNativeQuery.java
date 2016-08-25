package com.embracesource.jpa.nativejpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.embracesource.jpa.nativejpa.domain.Students;

public class TestNativeQuery {
	EntityManagerFactory emf = null;

	@Before
	public void before() {
		// 根据在persistence.xml中配置的persistence-unit name 创建EntityManagerFactory
		emf = Persistence.createEntityManagerFactory("embracesource");
	}

	@After
	public void after() {
		if (null != emf) {
			emf.close();
		}
	}

	/**
	 * 查询的结果是对象数组的集合
	 */
	@Test
	public void testNativeQuery1() {
		EntityManager em = emf.createEntityManager();
		// 定义SQL
		String sql = "SELECT * FROM students";
		// 创建原生SQL查询QUERY实例
		Query query = em.createNativeQuery(sql);
		// 执行查询，返回的是对象数组(Object[])列表,
		// 每一个对象数组存的是相应的实体属性
		@SuppressWarnings("unchecked")
		List<Object[]> objecArraytList = query.getResultList();

		for (int i = 0; i < objecArraytList.size(); i++) {
			Object[] obj = (Object[]) objecArraytList.get(i);
			System.out.println(obj[1] + ":" + obj[2]);
		}
		em.close();
	}

	/**
	 * 查询的结果是实体的集合
	 */
	@Test
	public void testNativeQuery2() {
		EntityManager em = emf.createEntityManager();
		// 定义SQL
		String sql = "SELECT * FROM students";
		// 创建原生SQL查询QUERY实例,指定了返回的实体类型
		Query query = em.createNativeQuery(sql, Students.class);
		// 执行查询，返回的是实体列表,
		@SuppressWarnings("unchecked")
		List<Students> students = query.getResultList();
		for (Students student : students) {
			System.out.println(student.getStudentCode() + ":"
					+ student.getStudentName());
		}
		em.close();
	}

	/**
	 * 查询单个属性 返回的是这个属性值的集合
	 */
	@Test
	public void testNativeQuery3() {
		EntityManager em = emf.createEntityManager();
		// 定义SQL
		String sql = "SELECT s.student_name FROM students s";
		// 创建原生SQL查询QUERY实例
		Query query = em.createNativeQuery(sql);
		// 执行查询，返回的是String类型的集合，因为name这个属性是String类型
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		for (String result : resultList) {
			System.out.println(result);
		}
		em.close();
	}

	/**
	 * 查询多个属性 返回的是这些属性值的数组的集合
	 */
	@Test
	public void testNativeQuery4() {
		EntityManager em = emf.createEntityManager();
		// 定义SQL
		String sql = "SELECT s.student_code, s.student_name FROM students s";
		// 创建原生SQL查询QUERY实例
		Query query = em.createNativeQuery(sql);
		// 执行查询，返回的是查询属性值数组的集合
		@SuppressWarnings("unchecked")
		List<Object[]> objecArraytList = query.getResultList();
		for (int i = 0; i < objecArraytList.size(); i++) {
			Object[] obj = (Object[]) objecArraytList.get(i);
			System.out.println(obj[0] + ":" + obj[1]);
		}
		em.close();
	}

	/**
	 * 向数据库红插入一条数据
	 */
	@Test
	public void testNativeQueryInsert() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// 定义SQL
		String sql = "INSERT INTO public.students(id, student_code, student_name) VALUES (1, '0001', '习近平')";
		// 创建原生SQL查询QUERY实例
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * 以对象的形式向数据库添加一条记录
	 * 
	 */
	@Test
	public void testNativeQueryInsert1() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Students student = new Students();
		student.setId(3l); student.setStudentCode("0003");
		student.setStudentName("王岐山"); em.persist(student);
		em.getTransaction().commit();
		
		em.close();
	}

	/**
	 * 更新一条记录
	 */
	@Test
	public void testNativeQueryUpdate() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "UPDATE public.students SET student_name='李源潮' WHERE id=2";
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();

		em.close();
	}

	/**
	 * 删除一条记录
	 */
	@Test
	public void testNativeQueryDelete() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "DELETE FROM public.students WHERE id=1";
		// 创建原生SQL查询QUERY实例
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();

		em.close();
	}
}
