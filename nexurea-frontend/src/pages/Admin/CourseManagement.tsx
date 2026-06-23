import React, { useEffect, useState } from 'react';
import { courseService } from '../../services/courseService';
import { CoursePackage, CourseVideo } from '../../types';
import '../Dashboard.css';

const CourseManagement: React.FC = () => {
  const [packages, setPackages] = useState<CoursePackage[]>([]);
  const [loading, setLoading] = useState(true);
  const [showPackageForm, setShowPackageForm] = useState(false);
  const [showVideoForm, setShowVideoForm] = useState(false);
  const [selectedPackage, setSelectedPackage] = useState<number | null>(null);
  const [packageForm, setPackageForm] = useState({
    name: '',
    description: '',
    price: 0,
    commissionRate: 0,
    duration: '',
    features: '',
  });
  const [videoForm, setVideoForm] = useState({
    title: '',
    description: '',
    videoUrl: '',
    duration: 0,
    orderIndex: 0,
  });

  useEffect(() => {
    loadPackages();
  }, []);

  const loadPackages = async () => {
    try {
      setLoading(true);
      const data = await courseService.getPackages();
      setPackages(data);
    } catch (err) {
      console.error('Failed to load packages:', err);
    } finally {
      setLoading(false);
    }
  };

  const handlePackageSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const data = {
        ...packageForm,
        features: packageForm.features.split('\n').filter((f) => f.trim()),
      };
      await courseService.createPackage(data);
      alert('Package created successfully!');
      setShowPackageForm(false);
      setPackageForm({
        name: '',
        description: '',
        price: 0,
        commissionRate: 0,
        duration: '',
        features: '',
      });
      loadPackages();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to create package');
    }
  };

  const handleVideoSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedPackage) return;
    try {
      await courseService.createVideo(selectedPackage, videoForm);
      alert('Video added successfully!');
      setShowVideoForm(false);
      setVideoForm({
        title: '',
        description: '',
        videoUrl: '',
        duration: 0,
        orderIndex: 0,
      });
      loadPackages();
    } catch (err: any) {
      alert(err.response?.data?.message || 'Failed to add video');
    }
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
      </div>
    );
  }

  return (
    <div className="dashboard-page">
      <div className="container">
        <div className="page-header">
          <div>
            <h1>Course Management</h1>
            <p>Create and manage course packages</p>
          </div>
          <button
            className="btn btn-primary"
            onClick={() => setShowPackageForm(!showPackageForm)}
          >
            {showPackageForm ? 'Cancel' : 'New Package'}
          </button>
        </div>

        {showPackageForm && (
          <div className="card mb-4" style={{ padding: '32px' }}>
            <h2 style={{ marginBottom: '24px', color: 'var(--primary-navy)' }}>Create Course Package</h2>
            <form onSubmit={handlePackageSubmit}>
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Package Name</label>
                  <input
                    type="text"
                    className="form-input"
                    value={packageForm.name}
                    onChange={(e) => setPackageForm({ ...packageForm, name: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Duration</label>
                  <input
                    type="text"
                    className="form-input"
                    value={packageForm.duration}
                    onChange={(e) => setPackageForm({ ...packageForm, duration: e.target.value })}
                    placeholder="e.g., 6 months"
                  />
                </div>
              </div>
              <div className="form-group">
                <label className="form-label">Description</label>
                <input
                  type="text"
                  className="form-input"
                  value={packageForm.description}
                  onChange={(e) => setPackageForm({ ...packageForm, description: e.target.value })}
                  required
                />
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label className="form-label">Price (₹)</label>
                  <input
                    type="number"
                    className="form-input"
                    value={packageForm.price || ''}
                    onChange={(e) => setPackageForm({ ...packageForm, price: parseInt(e.target.value) })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Commission Rate (%)</label>
                  <input
                    type="number"
                    className="form-input"
                    value={packageForm.commissionRate || ''}
                    onChange={(e) => setPackageForm({ ...packageForm, commissionRate: parseInt(e.target.value) })}
                    required
                  />
                </div>
              </div>
              <div className="form-group">
                <label className="form-label">Features (one per line)</label>
                <textarea
                  className="form-input"
                  rows={4}
                  value={packageForm.features}
                  onChange={(e) => setPackageForm({ ...packageForm, features: e.target.value })}
                  style={{ resize: 'vertical' }}
                />
              </div>
              <button type="submit" className="btn btn-primary">
                Create Package
              </button>
            </form>
          </div>
        )}

        <div className="grid grid-2">
          {packages.map((pkg) => (
            <div key={pkg.id} className="card">
              <h3 style={{ color: 'var(--primary-navy)', marginBottom: '12px' }}>{pkg.name}</h3>
              <p style={{ color: 'var(--text-light)', marginBottom: '16px' }}>{pkg.description}</p>
              <div style={{ display: 'flex', gap: '16px', marginBottom: '16px' }}>
                <div>
                  <strong>Price:</strong> ₹{pkg.price}
                </div>
                <div>
                  <strong>Commission:</strong> {pkg.commissionRate}%
                </div>
                <div>
                  <strong>Videos:</strong> {pkg.videoCount || 0}
                </div>
              </div>
              <button
                className="btn btn-secondary"
                onClick={() => {
                  setSelectedPackage(pkg.id);
                  setShowVideoForm(true);
                }}
                style={{ width: '100%' }}
              >
                Add Video
              </button>
            </div>
          ))}
        </div>

        {showVideoForm && selectedPackage && (
          <div
            style={{
              position: 'fixed',
              top: 0,
              left: 0,
              right: 0,
              bottom: 0,
              background: 'rgba(0,0,0,0.5)',
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
              zIndex: 1000,
            }}
            onClick={() => setShowVideoForm(false)}
          >
            <div
              className="card"
              style={{ maxWidth: '600px', width: '90%', padding: '32px' }}
              onClick={(e) => e.stopPropagation()}
            >
              <h2 style={{ marginBottom: '24px', color: 'var(--primary-navy)' }}>Add Video</h2>
              <form onSubmit={handleVideoSubmit}>
                <div className="form-group">
                  <label className="form-label">Video Title</label>
                  <input
                    type="text"
                    className="form-input"
                    value={videoForm.title}
                    onChange={(e) => setVideoForm({ ...videoForm, title: e.target.value })}
                    required
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Description</label>
                  <input
                    type="text"
                    className="form-input"
                    value={videoForm.description}
                    onChange={(e) => setVideoForm({ ...videoForm, description: e.target.value })}
                  />
                </div>
                <div className="form-group">
                  <label className="form-label">Video URL</label>
                  <input
                    type="url"
                    className="form-input"
                    value={videoForm.videoUrl}
                    onChange={(e) => setVideoForm({ ...videoForm, videoUrl: e.target.value })}
                    required
                  />
                </div>
                <div className="form-row">
                  <div className="form-group">
                    <label className="form-label">Duration (seconds)</label>
                    <input
                      type="number"
                      className="form-input"
                      value={videoForm.duration || ''}
                      onChange={(e) => setVideoForm({ ...videoForm, duration: parseInt(e.target.value) })}
                      required
                    />
                  </div>
                  <div className="form-group">
                    <label className="form-label">Order Index</label>
                    <input
                      type="number"
                      className="form-input"
                      value={videoForm.orderIndex || ''}
                      onChange={(e) => setVideoForm({ ...videoForm, orderIndex: parseInt(e.target.value) })}
                      required
                    />
                  </div>
                </div>
                <div style={{ display: 'flex', gap: '12px' }}>
                  <button type="submit" className="btn btn-primary">
                    Add Video
                  </button>
                  <button
                    type="button"
                    className="btn btn-secondary"
                    onClick={() => setShowVideoForm(false)}
                  >
                    Cancel
                  </button>
                </div>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default CourseManagement;
