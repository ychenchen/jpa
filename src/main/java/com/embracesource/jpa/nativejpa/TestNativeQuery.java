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
		// ������persistence.xml�����õ�persistence-unit name ����EntityManagerFactory
		emf = Persistence.createEntityManagerFactory("embracesource");
	}

	@After
	public void after() {
		if (null != emf) {
			emf.close();
		}
	}

	/**
	 * ��ѯ�Ľ���Ƕ�������ļ���
	 */
	@Test
	public void testNativeQuery1() {
		EntityManager em = emf.createEntityManager();
		// ����SQL
		String sql = "SELECT * FROM students";
		// ����ԭ��SQL��ѯQUERYʵ��
		Query query = em.createNativeQuery(sql);
		// ִ�в�ѯ�����ص��Ƕ�������(Object[])�б�,
		// ÿһ����������������Ӧ��ʵ������
		@SuppressWarnings("unchecked")
		List<Object[]> objecArraytList = query.getResultList();

		for (int i = 0; i < objecArraytList.size(); i++) {
			Object[] obj = (Object[]) objecArraytList.get(i);
			System.out.println(obj[1] + ":" + obj[2]);
		}
		em.close();
	}

	/**
	 * ��ѯ�Ľ����ʵ��ļ���
	 */
	@Test
	public void testNativeQuery2() {
		EntityManager em = emf.createEntityManager();
		// ����SQL
		String sql = "SELECT * FROM students";
		// ����ԭ��SQL��ѯQUERYʵ��,ָ���˷��ص�ʵ������
		Query query = em.createNativeQuery(sql, Students.class);
		// ִ�в�ѯ�����ص���ʵ���б�,
		@SuppressWarnings("unchecked")
		List<Students> students = query.getResultList();
		for (Students student : students) {
			System.out.println(student.getStudentCode() + ":"
					+ student.getStudentName());
		}
		em.close();
	}

	/**
	 * ��ѯ�������� ���ص����������ֵ�ļ���
	 */
	@Test
	public void testNativeQuery3() {
		EntityManager em = emf.createEntityManager();
		// ����SQL
		String sql = "SELECT s.student_name FROM students s";
		// ����ԭ��SQL��ѯQUERYʵ��
		Query query = em.createNativeQuery(sql);
		// ִ�в�ѯ�����ص���String���͵ļ��ϣ���Ϊname���������String����
		@SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
		for (String result : resultList) {
			System.out.println(result);
		}
		em.close();
	}

	/**
	 * ��ѯ������� ���ص�����Щ����ֵ������ļ���
	 */
	@Test
	public void testNativeQuery4() {
		EntityManager em = emf.createEntityManager();
		// ����SQL
		String sql = "SELECT s.student_code, s.student_name FROM students s";
		// ����ԭ��SQL��ѯQUERYʵ��
		Query query = em.createNativeQuery(sql);
		// ִ�в�ѯ�����ص��ǲ�ѯ����ֵ����ļ���
		@SuppressWarnings("unchecked")
		List<Object[]> objecArraytList = query.getResultList();
		for (int i = 0; i < objecArraytList.size(); i++) {
			Object[] obj = (Object[]) objecArraytList.get(i);
			System.out.println(obj[0] + ":" + obj[1]);
		}
		em.close();
	}

	/**
	 * �����ݿ�����һ������
	 */
	@Test
	public void testNativeQueryInsert() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		// ����SQL
		String sql = "INSERT INTO public.students(id, student_code, student_name) VALUES (1, '0001', 'ϰ��ƽ')";
		// ����ԭ��SQL��ѯQUERYʵ��
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * �Զ������ʽ�����ݿ����һ����¼
	 * 
	 */
	@Test
	public void testNativeQueryInsert1() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Students student = new Students();
		student.setId(3l); student.setStudentCode("0003");
		student.setStudentName("���ɽ"); em.persist(student);
		em.getTransaction().commit();
		
		em.close();
	}

	/**
	 * ����һ����¼
	 */
	@Test
	public void testNativeQueryUpdate() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "UPDATE public.students SET student_name='��Դ��' WHERE id=2";
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();

		em.close();
	}

	/**
	 * ɾ��һ����¼
	 */
	@Test
	public void testNativeQueryDelete() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		String sql = "DELETE FROM public.students WHERE id=1";
		// ����ԭ��SQL��ѯQUERYʵ��
		Query query = em.createNativeQuery(sql);
		query.executeUpdate();
		em.getTransaction().commit();

		em.close();
	}
}
