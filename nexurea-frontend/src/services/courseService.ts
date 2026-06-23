import api from './api';
import { CoursePackage, CourseVideo } from '../types';

export const courseService = {
  getPackages: async (): Promise<CoursePackage[]> => {
    const response = await api.get<CoursePackage[]>('/courses/packages');
    return response.data;
  },

  getPackageById: async (id: number): Promise<CoursePackage> => {
    const response = await api.get<CoursePackage>(`/courses/packages/${id}`);
    return response.data;
  },

  getVideos: async (packageId: number): Promise<CourseVideo[]> => {
    const response = await api.get<CourseVideo[]>(`/courses/packages/${packageId}/videos`);
    return response.data;
  },

  getUserCourses: async (): Promise<CoursePackage[]> => {
    const response = await api.get<CoursePackage[]>('/courses/my-courses');
    return response.data;
  },

  // Admin functions
  createPackage: async (data: Partial<CoursePackage>): Promise<CoursePackage> => {
    const response = await api.post<CoursePackage>('/admin/courses/packages', data);
    return response.data;
  },

  updatePackage: async (id: number, data: Partial<CoursePackage>): Promise<CoursePackage> => {
    const response = await api.put<CoursePackage>(`/admin/courses/packages/${id}`, data);
    return response.data;
  },

  deletePackage: async (id: number): Promise<void> => {
    await api.delete(`/admin/courses/packages/${id}`);
  },

  createVideo: async (packageId: number, data: Partial<CourseVideo>): Promise<CourseVideo> => {
    const response = await api.post<CourseVideo>(`/admin/courses/packages/${packageId}/videos`, data);
    return response.data;
  },

  updateVideo: async (videoId: number, data: Partial<CourseVideo>): Promise<CourseVideo> => {
    const response = await api.put<CourseVideo>(`/admin/courses/videos/${videoId}`, data);
    return response.data;
  },

  deleteVideo: async (videoId: number): Promise<void> => {
    await api.delete(`/admin/courses/videos/${videoId}`);
  },
};
