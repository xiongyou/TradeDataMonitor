package com.tradedatamonitor.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;

import com.tradedatamonitor.util.HibernateSessionFactory;

public class BaseDAO {

	/**
	 * 插入数据
	 * 
	 * @param object
	 */
	public <T> void create(T object) {

		Session session = HibernateSessionFactory.getSessionFactory()
				.openSession();

		try {
			session.beginTransaction();

			session.persist(object);

			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	/**
	 * 更新数据库
	 * 
	 * @param object
	 */
	public <T> void update(T object) {

		Session session = HibernateSessionFactory.getSessionFactory()
				.openSession();

		try {
			session.beginTransaction();
			session.update(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	/**
	 * 从数据库中删除
	 * 
	 * @param object
	 */
	public <T> void delete(T object) {

		Session session = HibernateSessionFactory.getSessionFactory()
				.openSession();

		try {
			session.beginTransaction();
			session.delete(object);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
		} finally {
			session.close();
		}
	}

	/**
	 * 查找单个Entity Bean
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(Class<? extends T> clazz, Serializable id) {

		Session session = HibernateSessionFactory.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			return (T) session.get(clazz, id);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}

	/**
	 * 查找多个Entity Bean
	 * 
	 * @param hql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<?> list(String hql) {

		Session session = HibernateSessionFactory.getSessionFactory()
				.openSession();
		try {
			session.beginTransaction();
			return session.createQuery(hql).list();
		} finally {
			session.getTransaction().commit();
			session.close();
		}
	}
}