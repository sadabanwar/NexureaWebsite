import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import ReactPlayer from 'react-player';
import { courseService } from '../services/courseService';
import { CourseVideo, CoursePackage } from '../types';
import './VideoPage.css';

const VideoPage: React.FC = () => {
  const { packageId } = useParams<{ packageId: string }>();
  const [videos, setVideos] = useState<CourseVideo[]>([]);
  const [coursePackage, setCoursePackage] = useState<CoursePackage | null>(null);
  const [currentVideo, setCurrentVideo] = useState<CourseVideo | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (packageId) {
      loadCourse(parseInt(packageId));
    }
  }, [packageId]);

  const loadCourse = async (id: number) => {
    try {
      setLoading(true);
      const [pkg, vids] = await Promise.all([
        courseService.getPackageById(id),
        courseService.getVideos(id),
      ]);
      setCoursePackage(pkg);
      setVideos(vids.sort((a, b) => a.orderIndex - b.orderIndex));
      if (vids.length > 0) {
        setCurrentVideo(vids[0]);
      }
    } catch (err) {
      console.error('Failed to load course:', err);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
      </div>
    );
  }

  if (!coursePackage) {
    return (
      <div className="container mt-5">
        <div className="error-message">Course not found</div>
      </div>
    );
  }

  return (
    <div className="video-page">
      <div className="video-container">
        <div className="video-player-section">
          {currentVideo ? (
            <>
              <div className="player-wrapper">
                <ReactPlayer
                  url={currentVideo.videoUrl}
                  controls
                  width="100%"
                  height="100%"
                  config={{
                    youtube: {
                      playerVars: { showinfo: 1 }
                    }
                  }}
                />
              </div>
              <div className="video-info">
                <h1>{currentVideo.title}</h1>
                <p>{currentVideo.description}</p>
              </div>
            </>
          ) : (
            <div className="no-video-message">
              <p>No videos available for this course yet.</p>
            </div>
          )}
        </div>

        <div className="video-sidebar">
          <div className="course-header">
            <h2>{coursePackage.name}</h2>
            <p>{videos.length} Videos</p>
          </div>

          <div className="video-list">
            {videos.map((video, index) => (
              <div
                key={video.id}
                className={`video-item ${currentVideo?.id === video.id ? 'active' : ''}`}
                onClick={() => setCurrentVideo(video)}
              >
                <div className="video-number">{index + 1}</div>
                <div className="video-details">
                  <h4>{video.title}</h4>
                  <span className="video-duration">
                    {Math.floor(video.duration / 60)}:{String(video.duration % 60).padStart(2, '0')}
                  </span>
                </div>
                {currentVideo?.id === video.id && (
                  <div className="playing-indicator">
                    <svg viewBox="0 0 24 24" fill="currentColor">
                      <polygon points="5 3 19 12 5 21 5 3"/>
                    </svg>
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default VideoPage;
