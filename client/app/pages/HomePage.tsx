import { Navbar } from '@/components/layout/Navbar'
import { Footer } from '@/components/layout/Footer'
import { HeroSection } from '@/components/home/HeroSection'
import { StatsBar } from '@/components/home/StatsBar'
import { CinematicColumns } from '@/components/home/CinematicColumns'
import { CatalogSection } from '@/components/home/CatalogSection'

export function HomePage() {
  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      <main className="flex-1">
        <HeroSection />
        <StatsBar />
        <CinematicColumns />
        <CatalogSection />
      </main>
      <Footer />
    </div>
  )
}
