# Frontend Design Guidance

This document outlines the design principles and UI standards for the Recruitment Platform frontend.

## 1. Core Design Principles

- **Visual Hierarchy**: Prioritize a clean and clear visual structure to guide user attention.
- **Consistent Spacing**: Use a standard spacing system (e.g., 4px/8px increments) for all layouts.
- **Readable Typography**: Ensure high legibility for job descriptions and professional profiles.
- **Modern Aesthetics**:
    - Lightweight shadows for depth.
    - Card-based layouts for modularity.
    - Restrained and professional color palette.

## 2. Product Context Standards

UI decisions should align with the specific needs of a recruitment platform:
- **Job Discovery**: Focused on searchability and card-based listing.
- **Recruiter Workflows**: Efficient dashboard and management interfaces.
- **Candidate Workflows**: Intuitive resume building and application tracking.
- **Interviews & Messaging**: Clear, real-time communication UI.

## 3. Technology & Framework Constraints

The current implementation uses **Vue 3 + TypeScript + Vite**.

- **Component Library**: [Element Plus](https://element-plus.org/)
- **Styling**: Maintain compatibility with the existing Vue ecosystem.
- **Motion**: Use Vue-compatible animation solutions.

*Note: While `shadcn/ui` and `reactbits` are preferred for React projects, they should not be forced into this Vue codebase.*

## 4. Visual References

The following design concepts are used as references for the platform:

### 4.1 Home & Hero Section
- **Home View**: `frontend-home.png`
- **Wellfound Style**: `frontend-home-wellfound-style.png`
- **Hero Keywords**: `hero-centered-keywords.png`
- **Floating Chips**: `hero-floating-chips-recruitment.png`

### 4.2 Module Designs
- **Job Cards**: `hero-jobcards-core.png`
- **Navbar Update**: `navbar-update.png`
- **Minimal Jobs**: `home-minimal-jobs.png`

---
*Generated based on AGENTS.md project rules.*
